package net.pryoscode.decompiler.window.menu.help.items

import java.awt.Desktop
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JMenuItem

class About : JMenuItem("About", KeyEvent.VK_A), ActionListener {

    init {
        addActionListener(this)

        if (Desktop.getDesktop().isSupported(Desktop.Action.APP_ABOUT)) {
            Desktop.getDesktop().setAboutHandler { actionPerformed(null) }
            isVisible = false
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        net.pryoscode.decompiler.window.popup.About()
    }

}