package dev.shota.decompiler.window.sidebar

import javafx.scene.control.TreeItem
import java.util.jar.JarEntry

class Item(entries: ArrayList<JarEntry>, private val entry: Entry) : TreeItem<Entry>() {

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
                        if (e.isDirectory) dirs.add(Entry(entry.file, e)) else files.add(Entry(entry.file, e))
                    }
                }
            }
            dirs.sortWith { e1, e2 -> e1.name.compareTo(e2.name) }
            files.sortWith { e1, e2 -> e1.name.compareTo(e2.name) }
            for (e in dirs) children.add(Item(entries, e))
            for (e in files) children.add(Item(entries, e))
        }

        if (entry.type == Type.PACKAGE) {
            val dirs = ArrayList<Entry>()
            val files = ArrayList<Entry>()
            for (e in entries) {
                if (e.name.startsWith(entry.path) && !e.name.equals(entry.path)) {
                    val sub = e.name.substring(entry.path.length)
                    if (e.isDirectory && !sub.substringBeforeLast("/").contains("/"))
                        dirs.add(Entry(entry.file, e))
                    else if (!sub.contains("/"))
                        files.add(Entry(entry.file, e))
                }
            }
            dirs.sortWith { e1, e2 -> e1.name.compareTo(e2.name) }
            files.sortWith { e1, e2 -> e1.name.compareTo(e2.name) }
            for (e in dirs) children.add(Item(entries, e))
            for (e in files) children.add(Item(entries, e))
        }
    }

    override fun isLeaf(): Boolean {
        return entry.type != Type.ARCHIVE && entry.type != Type.PACKAGE
    }

}