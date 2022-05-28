package net.pryoscode.decompiler.window.menu.edit.menus

import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class Find : JMenuItem("Find", KeyEvent.VK_F) {

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK)
        isEnabled = false
    }

}