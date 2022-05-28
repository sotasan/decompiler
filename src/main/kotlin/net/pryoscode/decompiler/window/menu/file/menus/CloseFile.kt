package net.pryoscode.decompiler.window.menu.file.menus

import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class CloseFile : JMenuItem("Close File", KeyEvent.VK_W) {

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK)
        isEnabled = false
    }

}