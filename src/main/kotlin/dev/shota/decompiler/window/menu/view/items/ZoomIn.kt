package dev.shota.decompiler.window.menu.view.items

import dev.shota.decompiler.window.container.Code
import dev.shota.decompiler.window.container.Container
import dev.shota.decompiler.window.menu.MenuItem
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

class ZoomIn : MenuItem("view.zoomIn", KeyEvent.VK_PLUS) {

    init {
        isEnabled = false

        Container.selectionModel.selectedItemProperty().addListener { _, _, value ->
            isEnabled = value != null
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        Code.changeZoom(0.25)
    }

}