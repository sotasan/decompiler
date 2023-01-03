package com.hohltier.decompiler.window.explorer

import javafx.scene.control.TreeView

object Explorer : TreeView<ExplorerEntry>() {

    init {
        minWidth = 100.0
        setCellFactory { ExplorerCell() }
    }

}