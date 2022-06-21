package dev.shota.decompiler.window.menu.help

import dev.shota.decompiler.window.menu.Menu
import dev.shota.decompiler.window.menu.help.items.About
import java.awt.Desktop
import java.awt.event.KeyEvent

class Help : Menu("help", KeyEvent.VK_H) {

    init {
        isVisible = !Desktop.getDesktop().isSupported(Desktop.Action.APP_ABOUT)

        add(About())
    }

}