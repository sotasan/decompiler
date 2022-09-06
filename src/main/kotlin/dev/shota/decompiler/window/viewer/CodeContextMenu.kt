package dev.shota.decompiler.window.viewer

import dev.shota.decompiler.window.utils.Language
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem

object CodeContextMenu : ContextMenu() {

    init {
        val close = MenuItem(Language.get("code.close"))
        val closeOthers = MenuItem(Language.get("code.closeOthers"))
        val closeAll = MenuItem(Language.get("code.closeAll"))

        close.setOnAction { Viewer.tabs.remove(Viewer.selectionModel.selectedItem) }

        items.addAll(close)
    }

}