package net.pryoscode.decompiler.windows.components

import javafx.scene.control.TreeCell
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import org.kordamp.ikonli.feather.Feather
import org.kordamp.ikonli.javafx.FontIcon
import java.io.File
import java.util.jar.JarEntry
import java.util.jar.JarFile

class Sidebar(private val container: Container) : TreeView<Sidebar.Entry>() {

    init {
        setCellFactory { Cell(container) }
    }

    fun open(file: File) {
        if (!file.exists()) return
        container.tabs.clear()
        val entries = ArrayList<JarEntry>()
        val jar = JarFile(file).entries()
        while (jar.hasMoreElements())
            entries.add(jar.nextElement())
        root = Item(entries, Entry(file.name, file.absolutePath, Type.ARCHIVE))
        selectionModel.select(root)
    }


    class Entry(val name: String, val path: String, val type: Type)
    enum class Type { ARCHIVE, PACKAGE, CLASS, FILE }

    class Item(entries: ArrayList<JarEntry>, entry: Entry) : TreeItem<Entry>() {

        init {
            value = entry
            isExpanded = entry.type == Type.ARCHIVE

            if (entry.type == Type.ARCHIVE) {
                val control = ArrayList<String>()
                val dirs = ArrayList<Entry>()
                val files = ArrayList<Entry>()
                for (e in entries) {
                    if (e.isDirectory || !e.name.contains("/")) {
                        val name = e.name.split("/")[0]
                        if (!control.contains(name)) {
                            control.add(name)
                            val t = Entry(name, e.name, if (e.isDirectory) Type.PACKAGE else if (name.endsWith(".class", true)) Type.CLASS else Type.FILE)
                            if (e.isDirectory) dirs.add(t) else files.add(t)
                        }
                    }
                }
                dirs.sortWith { e1, e2 -> e1.name.compareTo(e2.name) }
                files.sortWith { e1, e2 -> e1.name.compareTo(e2.name) }
                for (e in dirs) children.add(Item(entries, e))
                for (e in files) children.add(Item(entries, e))
            }
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
                event.consume()
        }

    }

}