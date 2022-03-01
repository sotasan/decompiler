package net.pryoscode.decompiler.window.sidebar

import javafx.scene.control.TreeItem
import java.util.jar.JarEntry

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