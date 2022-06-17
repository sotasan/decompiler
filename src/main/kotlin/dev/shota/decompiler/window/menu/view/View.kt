package dev.shota.decompiler.window.menu.view

import dev.shota.decompiler.window.menu.view.items.ZoomIn
import dev.shota.decompiler.window.menu.view.items.ZoomOut
import dev.shota.decompiler.window.menu.view.items.ZoomReset
import dev.shota.decompiler.window.utils.language
import java.awt.event.KeyEvent
import javax.swing.JMenu

class View : JMenu(language("view")) {

    init {
        mnemonic = KeyEvent.VK_V
        add(ZoomIn())
        add(ZoomOut())
        add(ZoomReset())
    }

}