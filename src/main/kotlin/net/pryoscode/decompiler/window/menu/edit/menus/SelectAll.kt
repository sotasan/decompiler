package net.pryoscode.decompiler.window.menu.edit.menus

import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class SelectAll : JMenuItem("Select All", KeyEvent.VK_A) {

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK)
        isEnabled = false
    }

}