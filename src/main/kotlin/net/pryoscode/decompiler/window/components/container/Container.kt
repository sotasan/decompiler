package net.pryoscode.decompiler.window.components.container

import javafx.scene.control.TabPane
import net.pryoscode.decompiler.Decompiler
import net.pryoscode.decompiler.window.components.sidebar.Entry

class Container : TabPane() {

    init {
        tabDragPolicy = TabDragPolicy.REORDER
        tabClosingPolicy = TabClosingPolicy.ALL_TABS
    }

    fun open(entry: Entry) {
        var tab: Code? = null
        for (t in tabs) {
            if ((t as Code).entry == entry) {
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