package dev.shota.decompiler.old.menu.file.items

import javafx.application.Platform
import dev.shota.decompiler.window.container.Container
import dev.shota.decompiler.window.menu.MenuItem
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

class CloseTab : MenuItem("file.closeTab", KeyEvent.VK_W) {

    init {
        isEnabled = false
        Container.selectionModel.selectedItemProperty().addListener { _, _, value ->
            isEnabled = value != null
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        Platform.runLater {
            Container.tabs.remove(Container.selectionModel.selectedItem)
        }
    }

}