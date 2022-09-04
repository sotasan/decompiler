package dev.shota.decompiler.old.container

import com.formdev.flatlaf.util.SystemInfo
import javafx.scene.control.TabPane

object Container : TabPane() {

    init {
        minWidth = 300.0
        tabClosingPolicy = TabClosingPolicy.ALL_TABS
        tabDragPolicy = if (SystemInfo.isMacOS && SystemInfo.isMacFullWindowContentSupported) TabDragPolicy.FIXED else TabDragPolicy.REORDER
    }

}