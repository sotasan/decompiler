package dev.shota.decompiler.window.container

import com.formdev.flatlaf.util.SystemInfo
import dev.shota.decompiler.window.sidebar.Entry
import javafx.scene.control.TabPane

object Container : TabPane() {

    init {
        minWidth = 300.0
        tabClosingPolicy = TabClosingPolicy.ALL_TABS
        tabDragPolicy = if (SystemInfo.isMacOS && SystemInfo.isMacFullWindowContentSupported) TabDragPolicy.FIXED else TabDragPolicy.REORDER
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
            Code(entry)
        else
            selectionModel.select(tab)
    }

}