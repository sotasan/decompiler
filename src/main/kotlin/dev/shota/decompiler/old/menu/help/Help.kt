package dev.shota.decompiler.old.menu.help

import dev.shota.decompiler.old.menu.Menu
import dev.shota.decompiler.old.menu.help.items.About
import java.awt.Desktop
import java.awt.event.KeyEvent

class Help : Menu("help", KeyEvent.VK_H) {

    init {
        isVisible = Desktop.isDesktopSupported() && !Desktop.getDesktop().isSupported(Desktop.Action.APP_ABOUT)

        add(About())
    }

}