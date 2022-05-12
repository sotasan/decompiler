package net.pryoscode.decompiler

import javafx.scene.control.TabPane
import net.pryoscode.decompiler.window.components.code.Preview
import net.pryoscode.decompiler.window.components.sidebar.Entry
import org.jetbrains.java.decompiler.main.Fernflower
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences
import org.jetbrains.java.decompiler.main.extern.IResultSaver
import org.jetbrains.java.decompiler.struct.ContextUnit
import org.jetbrains.java.decompiler.struct.StructClass
import org.jetbrains.java.decompiler.struct.StructContext
import org.jetbrains.java.decompiler.struct.lazy.LazyLoader
import org.jetbrains.java.decompiler.util.DataInputFullStream
import org.jetbrains.java.decompiler.util.InterpreterUtil
import java.io.File
import java.util.HashMap
import java.util.jar.Manifest

class Decompiler(private val tabPane: TabPane, private val entry: Entry) : IBytecodeProvider, IResultSaver, IFernflowerLogger() {

    private val bytes = InterpreterUtil.getBytes(entry.file, entry.entry)

    init {
        val fernflower = Fernflower(this, this, IFernflowerPreferences.DEFAULTS, this)
        fernflower.addLibrary(File("null.class"))

        val structContextField = fernflower.javaClass.getDeclaredField("structContext")
        structContextField.isAccessible = true
        val structContext = structContextField.get(fernflower) as StructContext

        val loaderField = structContext.javaClass.getDeclaredField("loader")
        loaderField.isAccessible = true
        val loader = loaderField.get(structContext) as LazyLoader

        val structClass = StructClass.create(DataInputFullStream(bytes), true, loader)
        structContext.classes.put(entry.name, structClass)

        val path = File("null.class").absolutePath
        val contextUnit = ContextUnit(ContextUnit.TYPE_FOLDER, null, path, true, this, fernflower)
        contextUnit.addClass(structClass, "null.class")

        val unitsField = structContext.javaClass.getDeclaredField("units")
        unitsField.isAccessible = true
        val units = unitsField.get(structContext) as HashMap<String, ContextUnit>

        units[path] = contextUnit
        loader.addClassLink(entry.name, LazyLoader.Link(path, null))

        fernflower.decompileContext()
    }

    override fun getBytecode(externalPath: String?, internalPath: String?): ByteArray {
        return bytes
    }

    override fun saveClassFile(path: String?, qualifiedName: String?, entryName: String?, content: String?, mapping: IntArray?) {
        content?.let {
            Preview(tabPane, entry, it)
        }
    }

    override fun saveFolder(path: String?) {}
    override fun copyFile(source: String?, path: String?, entryName: String?) {}
    override fun createArchive(path: String?, archiveName: String?, manifest: Manifest?) {}
    override fun saveDirEntry(path: String?, archiveName: String?, entryName: String?) {}
    override fun copyEntry(source: String?, path: String?, archiveName: String?, entry: String?) {}
    override fun saveClassEntry(path: String?, archiveName: String?, qualifiedName: String?, entryName: String?, content: String?) {}
    override fun closeArchive(path: String?, archiveName: String?) {}
    override fun writeMessage(message: String?, severity: Severity?) {}
    override fun writeMessage(message: String?, severity: Severity?, t: Throwable?) {}

}