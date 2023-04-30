package com.hohltier.decompiler.window.viewer

import com.formdev.flatlaf.util.SystemInfo
import com.hohltier.decompiler.window.utils.Styles
import javafx.collections.ListChangeListener
import javafx.scene.control.TabPane

object Viewer : TabPane() {

    init {
        minWidth = 300.0

        tabs.addListener(ListChangeListener {
            it.next()
            if (it.wasAdded()) selectionModel.select(it.addedSubList.first())
        })
    }

}