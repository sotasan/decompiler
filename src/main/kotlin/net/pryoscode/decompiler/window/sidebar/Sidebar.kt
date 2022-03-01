package net.pryoscode.decompiler.window.sidebar

import javafx.scene.control.TreeView
import javafx.scene.layout.BorderPane
import net.pryoscode.decompiler.window.code.Container
import java.io.File
import java.util.jar.JarEntry
import java.util.jar.JarFile

class Sidebar(private val container: Container) : BorderPane() {

    private val tree = TreeView<Entry>()

    init {
        tree.setCellFactory { Cell(container) }
        center = tree
        right = Bar(tree)
    }

    fun open(file: File) {
        if (!file.exists()) return
        container.tabs.clear()
        val entries = ArrayList<JarEntry>()
        val jar = JarFile(file).entries()
        while (jar.hasMoreElements())
            entries.add(jar.nextElement())
        val entry = Entry(null)
        entry.name = file.name
        entry.path = file.absolutePath
        entry.type = Type.ARCHIVE
        tree.root = Item(entries, entry)
        tree.selectionModel.select(tree.root)
    }

}