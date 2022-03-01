package net.pryoscode.decompiler.window.code

import javafx.scene.control.TabPane
import net.pryoscode.decompiler.window.sidebar.Entry

class Container : TabPane() {

    init {
        tabDragPolicy = TabDragPolicy.REORDER
        tabClosingPolicy = TabClosingPolicy.ALL_TABS
    }

    fun open(entry: Entry) {
        val tab = Preview(entry)
        tabs.add(tab)
        selectionModel.select(tab)
    }

}