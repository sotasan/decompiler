package net.pryoscode.decompiler.window.menu.help.items

import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.JMenuItem

class About : JMenuItem("About", KeyEvent.VK_A) {

    init {
        addActionListener(::actionListener)
    }

    private fun actionListener(event: ActionEvent) {
        net.pryoscode.decompiler.window.popup.About()
    }

}