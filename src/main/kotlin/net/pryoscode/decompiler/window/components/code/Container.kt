package net.pryoscode.decompiler.window.components.code

import javafx.scene.control.TabPane
import net.pryoscode.decompiler.Decompiler
import net.pryoscode.decompiler.window.components.sidebar.Entry

class Container : TabPane() {

    init {
        tabDragPolicy = TabDragPolicy.REORDER
        tabClosingPolicy = TabClosingPolicy.ALL_TABS
    }

    fun open(entry: Entry) {
        var tab: Preview? = null
        for (t in tabs) {
            if ((t as Preview).entry == entry) {
                tab = t
                break
            }
        }
        if (tab == null)
            Decompiler(this, entry)
        else
            selectionModel.select(tab)
    }

}