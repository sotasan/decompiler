package dev.shota.decompiler.window.explorer

import javafx.scene.control.TreeView

object Explorer : TreeView<String>() {

    init {
        minWidth = 100.0
    }

}