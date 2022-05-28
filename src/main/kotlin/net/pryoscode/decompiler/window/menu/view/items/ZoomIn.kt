package net.pryoscode.decompiler.window.menu.view.items

import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class ZoomIn : JMenuItem("Zoom In", KeyEvent.VK_PLUS) {

    init {
        isEnabled = false
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, KeyEvent.CTRL_DOWN_MASK)
        addActionListener(::actionListener)
    }

    private fun actionListener(event: ActionEvent) {}

}