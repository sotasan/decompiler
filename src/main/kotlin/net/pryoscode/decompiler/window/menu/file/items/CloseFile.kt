package net.pryoscode.decompiler.window.menu.file.items

import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class CloseFile : JMenuItem("Close File", KeyEvent.VK_W) {

    init {
        isEnabled = false
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK)
        addActionListener(::actionListener)
    }

    private fun actionListener(event: ActionEvent) {}

}