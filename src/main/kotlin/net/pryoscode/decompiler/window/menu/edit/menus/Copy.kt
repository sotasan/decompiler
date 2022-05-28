package net.pryoscode.decompiler.window.menu.edit.menus

import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class Copy : JMenuItem("Copy", KeyEvent.VK_C) {

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK)
        isEnabled = false
    }

}