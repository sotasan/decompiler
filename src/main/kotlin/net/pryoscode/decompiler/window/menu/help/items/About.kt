package net.pryoscode.decompiler.window.menu.help.items

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JMenuItem

class About : JMenuItem("About", KeyEvent.VK_A), ActionListener {

    init {
        addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent?) {
        net.pryoscode.decompiler.window.popup.About()
    }

}