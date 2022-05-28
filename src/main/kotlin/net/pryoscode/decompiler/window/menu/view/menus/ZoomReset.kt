package net.pryoscode.decompiler.window.menu.view.menus

import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class ZoomReset : JMenuItem("Zoom Reset", KeyEvent.VK_0) {

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_0, KeyEvent.CTRL_DOWN_MASK)
        isEnabled = false
    }

}