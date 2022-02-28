package net.pryoscode.decompiler.windows.components

import javafx.scene.control.TreeCell
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import org.kordamp.ikonli.feather.Feather
import org.kordamp.ikonli.javafx.FontIcon
import java.io.File
import java.util.jar.JarFile

class Sidebar(private val container: Container) : TreeView<Sidebar.Entry>() {

    init {
        setCellFactory { Cell(container) }
    }

    fun open(file: File) {
        if (!file.exists()) return
        root = Item(Entry(file.name, file.absolutePath, Type.ARCHIVE))
        val entries = JarFile(file).entries()
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()
            if (entry.isDirectory)
                root.children.add(Item(Entry(entry.name, entry.name, Type.PACKAGE)))
        }
        container.tabs.clear()
    }


    class Entry(val name: String, val path: String, val type: Type)
    enum class Type { ARCHIVE, PACKAGE, CLASS, FILE  }

    class Item(entry: Entry) : TreeItem<Entry>() {

        init {
            value = entry
            isExpanded = entry.type == Type.ARCHIVE
        }

    }

    class Cell(private val container: Container) : TreeCell<Entry>() {

        init {
            addEventHandler(MouseEvent.MOUSE_CLICKED, ::mouseClicked)
            addEventHandler(MouseEvent.MOUSE_PRESSED, ::mousePressed)
        }

        override fun updateItem(item: Entry?, empty: Boolean) {
            super.updateItem(item, empty)
            disclosureNode = null
            if (empty || item == null) {
                text = ""
                graphic = null
            } else {
                text = item.name
                graphic = when (item.type) {
                    Type.ARCHIVE -> FontIcon(Feather.ARCHIVE)
                    Type.PACKAGE -> FontIcon(Feather.PACKAGE)
                    Type.CLASS -> FontIcon(Feather.CODE)
                    Type.FILE -> FontIcon(Feather.FILE)
                }
            }
        }

        private fun mouseClicked(event: MouseEvent) {
            if (item != null && event.button == MouseButton.PRIMARY) {
                if (item.type == Type.CLASS)
                    container.open(item)
                if (item.type == Type.PACKAGE)
                    treeItem.isExpanded = !treeItem.isExpanded
            }
            event.consume()
        }

        private fun mousePressed(event: MouseEvent) {
            if (event.clickCount % 2 == 0 && event.button == MouseButton.PRIMARY)
                event.consume();
        }

    }

}