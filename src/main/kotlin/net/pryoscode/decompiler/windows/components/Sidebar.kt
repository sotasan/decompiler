package net.pryoscode.decompiler.windows.components

import javafx.scene.control.TreeCell
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.input.MouseEvent
import java.io.File
import java.util.jar.JarFile

class Sidebar(private val container: Container) : TreeView<String>() {

    init {
        setCellFactory { Cell() }
    }

    fun open(file: File) {
        val entries = JarFile(file).entries()
        while (entries.hasMoreElements()) {
            val name = entries.nextElement().name
            if (!name.startsWith("META-INF/") && name.endsWith(".class")) {
                println(name)
            }
        }
        container.tabs.clear()
        container.open("", file.absolutePath)
    }

    class Item : TreeItem<String>() {

        init {}

    }

    class Cell : TreeCell<String>() {

        init {
            addEventHandler(MouseEvent.MOUSE_CLICKED, ::mouseClicked)
        }

        private fun mouseClicked(event: MouseEvent) {

        }



    }

}