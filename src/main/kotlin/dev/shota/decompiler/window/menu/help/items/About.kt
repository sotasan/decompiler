package dev.shota.decompiler.window.menu.help.items

import dev.shota.decompiler.window.popup.About
import dev.shota.decompiler.window.utils.translate
import java.awt.Desktop
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JMenuItem

class About : JMenuItem(translate("help.about"), KeyEvent.VK_A), ActionListener {

    init {
        addActionListener(this)

        if (Desktop.getDesktop().isSupported(Desktop.Action.APP_ABOUT)) {
            Desktop.getDesktop().setAboutHandler { actionPerformed(null) }
            isVisible = false
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        About()
    }

}