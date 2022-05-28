package net.pryoscode.decompiler.window.menu.view.menus

import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class ZoomIn : JMenuItem("Zoom In", KeyEvent.VK_PLUS) {

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, KeyEvent.CTRL_DOWN_MASK)
        isEnabled = false
    }

}