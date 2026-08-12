package moe.sota.decompiler.services

import java.nio.charset.StandardCharsets
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import moe.sota.decompiler.models.ArchiveModel
import moe.sota.decompiler.models.BaseModel
import moe.sota.decompiler.models.FileModel
import moe.sota.decompiler.models.SearchEntry
import moe.sota.decompiler.models.SearchKind
import moe.sota.decompiler.models.SearchSymbol
import moe.sota.decompiler.types.ClassType
import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.asc
import org.ktorm.dsl.batchInsert
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.like
import org.ktorm.dsl.limit
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.orderBy
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import org.ktorm.support.mysql.MySqlDialect

private const val LIMIT = 10
private const val MAX_TEXT_SIZE = 1024 * 1024
private const val MAX_LINE_LENGTH = 200
private const val PROBE_SIZE = 8 * 1024

private val SYMBOL_KINDS =
    listOf(SearchKind.CLASS, SearchKind.METHOD, SearchKind.FIELD, SearchKind.STRING)

private object Files : Table<Nothing>("files") {
    val id = int("id").primaryKey()
    val nameLower = varchar("name_lower")
}

private object Symbols : Table<Nothing>("symbols") {
    val fileId = int("file_id")
    val kind = varchar("kind")
    val symbol = varchar("symbol")
    val symbolLower = varchar("symbol_lower")
}

class SearchService {
    // An unnamed in-memory URL gives every connection a database of its own, and ktorm opens one
    // connection per operation. Ktorm also quotes the columns whose names are SQL keywords, which
    // H2 would otherwise read as case-sensitive. Ktorm ships no H2 dialect, and pagination needs
    // one, so this borrows the MySQL dialect, whose syntax H2 accepts.
    private val database =
        Database.connect(
            "jdbc:h2:mem:search;DB_CLOSE_DELAY=-1;CASE_INSENSITIVE_IDENTIFIERS=TRUE",
            dialect = MySqlDialect(),
        )
    // A scan reads this list while a second archive may be replacing it, so loading swaps the
    // reference instead of clearing the list a scan is walking.
    @Volatile private var fileModels: List<FileModel> = emptyList()

    init {
        execute(
            "create table if not exists files (id int primary key, name_lower varchar)",
            "create table if not exists symbols (" +
                "file_id int, kind varchar, symbol varchar, symbol_lower varchar)",
            "create index if not exists symbols_kind on symbols (kind, symbol_lower)",
        )
    }

    fun load(archive: ArchiveModel) {
        dispose()

        val files = ArrayList<FileModel>()
        collect(archive, files)
        if (files.isEmpty()) return

        fileModels = files

        database.batchInsert(Files) {
            for ((id, fileModel) in files.withIndex()) item {
                set(Files.id, id)
                set(Files.nameLower, fileModel.name.lowercase())
            }
        }

        val symbols =
            files
                .withIndex()
                .filter { it.value.type is ClassType }
                .flatMap { (id, fileModel) ->
                    index(fileModel).map { id to it }
                }
        if (symbols.isEmpty()) return

        database.batchInsert(Symbols) {
            for ((id, symbol) in symbols) item {
                set(Symbols.fileId, id)
                set(Symbols.kind, symbol.kind.name)
                set(Symbols.symbol, symbol.value)
                set(Symbols.symbolLower, symbol.value.lowercase())
            }
        }
    }

    fun search(query: String): List<SearchEntry.Result> {
        if (query.isBlank()) return emptyList()

        val pattern = "%${query.lowercase()}%"

        return database.useTransaction {
            val files =
                database
                    .from(Files)
                    .select(Files.id)
                    .where { Files.nameLower like pattern }
                    .orderBy(Files.nameLower.asc())
                    .limit(LIMIT)
                    .mapNotNull { row ->
                        val fileModel = fileModels.getOrNull(row[Files.id]!!)
                        fileModel?.let {
                            SearchEntry.Result(SearchKind.FILE, it.name, it.path, it)
                        }
                    }

            files + SYMBOL_KINDS.flatMap { symbols(it, pattern) }
        }
    }

    suspend fun searchContents(query: String): List<SearchEntry.Result> {
        if (query.isBlank()) return emptyList()

        val results = ArrayList<SearchEntry.Result>()
        val files = fileModels

        for (fileModel in files) {
            currentCoroutineContext().ensureActive()
            if (results.size >= LIMIT) break
            if (fileModel.type?.text == false || fileModel.size > MAX_TEXT_SIZE) continue

            val bytes =
                try {
                    fileModel.bytes
                } catch (e: Exception) {
                    e.printStackTrace(System.err)
                    continue
                }
            if (isBinary(bytes)) continue

            val text = String(bytes, StandardCharsets.UTF_8)
            if (!text.contains(query, ignoreCase = true)) continue

            val line =
                text.lineSequence().withIndex().firstOrNull {
                    it.value.contains(query, ignoreCase = true)
                } ?: continue

            results.add(
                SearchEntry.Result(
                    SearchKind.TEXT,
                    line.value.trim().take(MAX_LINE_LENGTH),
                    "${fileModel.path}:${line.index + 1}",
                    fileModel,
                )
            )
        }

        return results
    }

    fun dispose() {
        fileModels = emptyList()
        execute("truncate table files", "truncate table symbols")
    }

    private fun symbols(kind: SearchKind, pattern: String): List<SearchEntry.Result> =
        database
            .from(Symbols)
            .select(Symbols.fileId, Symbols.symbol)
            .where { (Symbols.kind eq kind.name) and (Symbols.symbolLower like pattern) }
            .orderBy(Symbols.symbolLower.asc())
            .limit(LIMIT)
            .mapNotNull { row ->
                val fileModel = fileModels.getOrNull(row[Symbols.fileId]!!)
                fileModel?.let {
                    SearchEntry.Result(kind, row[Symbols.symbol]!!, it.path, it)
                }
            }

    private fun index(fileModel: FileModel): List<SearchSymbol> =
        try {
            indexSymbols(fileModel.bytes)
        } catch (e: Exception) {
            e.printStackTrace(System.err)
            emptyList()
        }

    private fun collect(baseModel: BaseModel, files: MutableList<FileModel>) {
        for (child in baseModel.children) if (child is FileModel) files.add(child)
        else collect(child, files)
    }

    private fun isBinary(bytes: ByteArray): Boolean {
        for (i in 0..<minOf(bytes.size, PROBE_SIZE)) if (bytes[i] == 0.toByte()) return true
        return false
    }

    private fun execute(vararg statements: String) {
        database.useConnection { connection ->
            connection.createStatement().use { statement ->
                for (sql in statements) statement.execute(sql)
            }
        }
    }
}
