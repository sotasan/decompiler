package dev.shota.decompiler.window.menu.help.items

import dev.shota.decompiler.window.menu.MenuItem
import dev.shota.decompiler.window.popup.About
import java.awt.Desktop
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

class About : MenuItem("help.about", KeyEvent.VK_A, 0, false) {

    init {
        if (Desktop.getDesktop().isSupported(Desktop.Action.APP_ABOUT)) {
            Desktop.getDesktop().setAboutHandler { actionPerformed(null) }
            isVisible = false
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        About()
    }

}