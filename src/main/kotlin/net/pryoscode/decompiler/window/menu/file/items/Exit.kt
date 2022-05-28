package net.pryoscode.decompiler.window.menu.file.items

import net.pryoscode.decompiler.window.Window
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class Exit : JMenuItem("Exit", KeyEvent.VK_Q) {

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK)
        addActionListener(::actionListener)
    }

    private fun actionListener(event: ActionEvent) {
        Window.dispose()
    }

}