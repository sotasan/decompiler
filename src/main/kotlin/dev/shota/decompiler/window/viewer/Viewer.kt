package dev.shota.decompiler.window.viewer

import com.formdev.flatlaf.util.SystemInfo
import javafx.scene.control.TabPane

object Viewer : TabPane() {

    init {
        minWidth = 300.0
        tabClosingPolicy = TabClosingPolicy.ALL_TABS
        tabDragPolicy = if (SystemInfo.isMacOS && SystemInfo.isMacFullWindowContentSupported) TabDragPolicy.FIXED else TabDragPolicy.REORDER
    }

}