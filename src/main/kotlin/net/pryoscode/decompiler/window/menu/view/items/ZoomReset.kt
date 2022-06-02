package net.pryoscode.decompiler.window.menu.view.items

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class ZoomReset : JMenuItem("Zoom Reset", KeyEvent.VK_0), ActionListener {

    init {
        isEnabled = false
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_0, KeyEvent.CTRL_DOWN_MASK)
        addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent?) {}

}