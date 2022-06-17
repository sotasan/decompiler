package dev.shota.decompiler.window.menu.help

import dev.shota.decompiler.window.menu.help.items.About
import dev.shota.decompiler.window.utils.language
import java.awt.Desktop
import java.awt.event.KeyEvent
import javax.swing.JMenu

class Help : JMenu(language("help")) {

    init {
        mnemonic = KeyEvent.VK_H
        isVisible = !Desktop.getDesktop().isSupported(Desktop.Action.APP_ABOUT)
        add(About())
    }

}