package dev.shota.decompiler.old.menu.help.items

import dev.shota.decompiler.old.menu.MenuItem
import dev.shota.decompiler.old.dialogs.About
import java.awt.Desktop
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

class About : MenuItem("help.about", KeyEvent.VK_A, 0, false) {

    init {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.APP_ABOUT)) {
            Desktop.getDesktop().setAboutHandler { actionPerformed(null) }
            isVisible = false
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        About()
    }

}