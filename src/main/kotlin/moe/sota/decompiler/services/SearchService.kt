package moe.sota.decompiler.services

import java.nio.charset.StandardCharsets
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import moe.sota.decompiler.models.ArchiveModel
import moe.sota.decompiler.models.BaseModel
import moe.sota.decompiler.models.FileModel
import moe.sota.decompiler.models.SearchEntry
import moe.sota.decompiler.models.SearchKind
import moe.sota.decompiler.types.ClassType
import moe.sota.decompiler.types.ImageType
import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.asc
import org.ktorm.dsl.batchInsert
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.innerJoin
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
import org.objectweb.asm.ClassReader

private const val LIMIT = 10
private const val MAX_TEXT_SIZE = 1024 * 1024
private const val MAX_LINE_LENGTH = 200
private const val PROBE_SIZE = 8 * 1024

private val SYMBOL_KINDS =
    listOf(SearchKind.CLASS, SearchKind.METHOD, SearchKind.FIELD, SearchKind.STRING)

private object Files : Table<Nothing>("files") {
    val id = int("id").primaryKey()
    val path = varchar("path")
    val name = varchar("name")
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
    private val fileModels = HashMap<Int, FileModel>()

    init {
        execute(
            "create table if not exists files (" +
                "id int primary key, path varchar, name varchar, name_lower varchar)",
            "create table if not exists symbols (" +
                "file_id int, kind varchar, symbol varchar, symbol_lower varchar)",
        )
    }

    fun load(archive: ArchiveModel) {
        dispose()

        val files = ArrayList<FileModel>()
        collect(archive, files)
        if (files.isEmpty()) return

        files.forEachIndexed { id, fileModel -> fileModels[id] = fileModel }

        database.batchInsert(Files) {
            for ((id, fileModel) in files.withIndex()) item {
                set(Files.id, id)
                set(Files.path, fileModel.path)
                set(Files.name, fileModel.name)
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
        val files =
            database
                .from(Files)
                .select(Files.id, Files.path, Files.name)
                .where { Files.nameLower like pattern }
                .orderBy(Files.nameLower.asc())
                .limit(LIMIT)
                .mapNotNull { row ->
                    result(SearchKind.FILE, row[Files.name]!!, row[Files.path]!!, row[Files.id]!!)
                }

        return files + SYMBOL_KINDS.flatMap { symbols(it, pattern) }
    }

    suspend fun searchContents(query: String): List<SearchEntry.Result> {
        if (query.isBlank()) return emptyList()

        val needle = query.lowercase()
        val results = ArrayList<SearchEntry.Result>()

        for (fileModel in fileModels.values.sortedBy { it.path }) {
            currentCoroutineContext().ensureActive()
            if (results.size >= LIMIT) break
            if (fileModel.type is ClassType || fileModel.type is ImageType) continue

            val bytes =
                try {
                    fileModel.bytes
                } catch (e: Exception) {
                    e.printStackTrace(System.err)
                    continue
                }
            if (bytes.size > MAX_TEXT_SIZE || isBinary(bytes)) continue

            val line =
                String(bytes, StandardCharsets.UTF_8).lineSequence().withIndex().firstOrNull {
                    it.value.lowercase().contains(needle)
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
        fileModels.clear()
        execute("truncate table files", "truncate table symbols")
    }

    private fun symbols(kind: SearchKind, pattern: String): List<SearchEntry.Result> =
        database
            .from(Symbols)
            .innerJoin(Files, on = Symbols.fileId eq Files.id)
            .select(Symbols.fileId, Symbols.symbol, Files.path)
            .where { (Symbols.kind eq kind.name) and (Symbols.symbolLower like pattern) }
            .orderBy(Symbols.symbolLower.asc())
            .limit(LIMIT)
            .mapNotNull { row ->
                result(kind, row[Symbols.symbol]!!, row[Files.path]!!, row[Symbols.fileId]!!)
            }

    private fun result(
        kind: SearchKind,
        label: String,
        detail: String,
        id: Int,
    ): SearchEntry.Result? {
        val fileModel = fileModels[id] ?: return null
        return SearchEntry.Result(kind, label, detail, fileModel)
    }

    private fun index(fileModel: FileModel): List<SearchSymbol> =
        try {
            val indexer = SearchIndexer()
            ClassReader(fileModel.bytes)
                .accept(indexer, ClassReader.SKIP_DEBUG or ClassReader.SKIP_FRAMES)
            indexer.symbols
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
