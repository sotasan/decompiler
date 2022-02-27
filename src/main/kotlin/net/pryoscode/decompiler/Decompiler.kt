package net.pryoscode.decompiler

import org.jetbrains.java.decompiler.main.Fernflower
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider
import org.jetbrains.java.decompiler.main.extern.IResultSaver
import java.util.jar.Manifest

class Decompiler : IBytecodeProvider, IResultSaver {

    init {
        Fernflower(this, this, null, null)
    }

    override fun getBytecode(externalPath: String?, internalPath: String?): ByteArray {
        TODO("Not yet implemented")
    }

    override fun saveFolder(path: String?) {}
    override fun copyFile(source: String?, path: String?, entryName: String?) {}
    override fun saveClassFile(path: String?, qualifiedName: String?, entryName: String?, content: String?, mapping: IntArray?) {}
    override fun createArchive(path: String?, archiveName: String?, manifest: Manifest?) {}
    override fun saveDirEntry(path: String?, archiveName: String?, entryName: String?) {}
    override fun copyEntry(source: String?, path: String?, archiveName: String?, entry: String?) {}
    override fun saveClassEntry(path: String?, archiveName: String?, qualifiedName: String?, entryName: String?, content: String?) {}
    override fun closeArchive(path: String?, archiveName: String?) {}

}