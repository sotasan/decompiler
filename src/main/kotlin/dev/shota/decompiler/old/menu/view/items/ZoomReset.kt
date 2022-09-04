package dev.shota.decompiler.old.menu.view.items

import dev.shota.decompiler.old.container.Code
import dev.shota.decompiler.old.container.Container
import dev.shota.decompiler.old.menu.MenuItem
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

class ZoomReset : MenuItem("view.zoomReset", KeyEvent.VK_0) {

    init {
        isEnabled = false

        Container.selectionModel.selectedItemProperty().addListener { _, _, value ->
            isEnabled = value != null
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        Code.setZoom(1.0)
    }

}