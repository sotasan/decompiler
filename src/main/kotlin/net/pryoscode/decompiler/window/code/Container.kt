package net.pryoscode.decompiler.window.code

import javafx.scene.control.TabPane
import net.pryoscode.decompiler.Decompiler
import net.pryoscode.decompiler.window.sidebar.Entry

class Container : TabPane() {

    init {
        tabDragPolicy = TabDragPolicy.REORDER
        tabClosingPolicy = TabClosingPolicy.ALL_TABS
    }

    fun open(entry: Entry) {
        Decompiler(this, entry)
    }

}