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
        var found = false
        for (tab in tabs) {
            val preview = tab as Preview
            if (preview.entry == entry)
                found = true
        }
        if (!found)
            Decompiler(this, entry)
    }

}