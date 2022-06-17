package dev.shota.decompiler.window.menu.view

import dev.shota.decompiler.window.menu.view.items.ZoomIn
import dev.shota.decompiler.window.menu.view.items.ZoomOut
import dev.shota.decompiler.window.menu.view.items.ZoomReset
import dev.shota.decompiler.window.utils.translate
import java.awt.event.KeyEvent
import javax.swing.JMenu

class View : JMenu(translate("view")) {

    init {
        mnemonic = KeyEvent.VK_V
        add(ZoomIn())
        add(ZoomOut())
        add(ZoomReset())
    }

}