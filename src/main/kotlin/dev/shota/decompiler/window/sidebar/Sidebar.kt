package dev.shota.decompiler.window.sidebar

import com.formdev.flatlaf.util.SystemInfo
import dev.shota.decompiler.window.container.Container
import javafx.scene.control.TreeView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import java.io.File
import java.util.jar.JarEntry
import java.util.jar.JarFile

object Sidebar : BorderPane() {

    private val tree = TreeView<Entry>()

    init {
        minWidth = 100.0

        if (SystemInfo.isMacOS && SystemInfo.isMacFullWindowContentSupported) {
            val space = Pane()
            space.prefHeight = 25.0
            top = space
        }

        center = tree
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
    }

    fun open(file: File) {
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