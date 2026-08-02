package moe.sota.decompiler.transformers

import java.io.File
import java.util.jar.Manifest
import moe.sota.decompiler.models.FileModel
import org.jetbrains.java.decompiler.main.Fernflower
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences
import org.jetbrains.java.decompiler.main.extern.IResultSaver

class VineflowerTransformer : IFernflowerLogger(), ITransformer, IBytecodeProvider, IResultSaver {
    private lateinit var fileModel: FileModel
    private lateinit var content: String

    override fun transform(fileModel: FileModel): String {
        this.fileModel = fileModel
        // TODO: Refactor
        Fernflower(this, this, IFernflowerPreferences.getDefaults(), this).apply {
            addSource(File(".class"))
            decompileContext()
        }
        return content.replace("   ", "    ")
    }

    override fun getBytecode(externalPath: String?, internalPath: String?): ByteArray =
        fileModel.bytes

    override fun saveClassFile(
        path: String?,
        qualifiedName: String?,
        entryName: String?,
        content: String,
        mapping: IntArray?,
    ) {
        this.content = content
    }

    override fun closeArchive(path: String?, archiveName: String?) {}

    override fun copyEntry(source: String?, path: String?, archiveName: String?, entry: String?) {}

    override fun copyFile(source: String?, path: String?, entryName: String?) {}

    override fun createArchive(path: String?, archiveName: String?, manifest: Manifest?) {}

    override fun saveClassEntry(
        path: String?,
        archiveName: String?,
        qualifiedName: String?,
        entryName: String?,
        content: String?,
    ) {}

    override fun saveDirEntry(path: String?, archiveName: String?, entryName: String?) {}

    override fun saveFolder(path: String?) {}

    override fun writeMessage(message: String?, severity: Severity?) {}

    override fun writeMessage(message: String?, severity: Severity?, t: Throwable?) {}
}
