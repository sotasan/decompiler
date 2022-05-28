package net.pryoscode.decompiler.window.menu.view.menus

import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class ZoomOut : JMenuItem("Zoom Out", KeyEvent.VK_MINUS) {

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, KeyEvent.CTRL_DOWN_MASK)
        isEnabled = false
    }

}