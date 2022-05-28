package net.pryoscode.decompiler.window.menu.edit.items

import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class SelectAll : JMenuItem("Select All", KeyEvent.VK_A) {

    init {
        isEnabled = false
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK)
        addActionListener(::actionListener)
    }

    private fun actionListener(event: ActionEvent) {}

}