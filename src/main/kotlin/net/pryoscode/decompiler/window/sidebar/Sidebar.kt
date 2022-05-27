package net.pryoscode.decompiler.window.sidebar

import javafx.scene.control.TreeView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.AnchorPane
import net.pryoscode.decompiler.window.container.Container
import java.io.File
import java.util.jar.JarEntry
import java.util.jar.JarFile

object Sidebar : AnchorPane() {

    private val tree = TreeView<Entry>()

    init {
        minWidth = 100.0

        tree.setCellFactory { Cell() }
        tree.addEventHandler(KeyEvent.KEY_PRESSED) {
            if (it.code == KeyCode.ENTER) {
                val item = tree.selectionModel.selectedItem
                if (item != null) {
                    if (item.value.type == Type.ARCHIVE || item.value.type == Type.PACKAGE)
                        item.isExpanded = !item.isExpanded
                    else if (item.value.type != Type.FILE)
                        Container.open(item.value)
                }
            }
        }
        setTopAnchor(tree, 0.0)
        setRightAnchor(tree, 0.0)
        setBottomAnchor(tree, 0.0)
        setLeftAnchor(tree, 0.0)

        val bar = Bar()
        setTopAnchor(bar, 0.0)
        setRightAnchor(bar, 0.0)
        setBottomAnchor(bar, 0.0)

        children.addAll(tree, bar)
    }

    fun open(file: File?) {
        if (file == null || !file.exists()) return
        Container.tabs.clear()
        val entries = ArrayList<JarEntry>()
        val jar = JarFile(file)
        val jarEntries = jar.entries()
        while (jarEntries.hasMoreElements())
            entries.add(jarEntries.nextElement())
        val entry = Entry(jar, null)
        entry.name = file.name
        entry.path = file.absolutePath
        entry.type = Type.ARCHIVE
        tree.root = Item(entries, entry)
        tree.selectionModel.select(tree.root)
    }

}