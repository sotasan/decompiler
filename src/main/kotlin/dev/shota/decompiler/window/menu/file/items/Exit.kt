package dev.shota.decompiler.window.menu.file.items

import dev.shota.decompiler.window.Window
import dev.shota.decompiler.window.menu.MenuItem
import java.awt.Desktop
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

class Exit : MenuItem("file.exit", KeyEvent.VK_Q) {

    init {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.APP_QUIT_HANDLER)) {
            Desktop.getDesktop().setQuitHandler { _, _ -> actionPerformed(null) }
            isVisible = false
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        Window.dispose()
    }

}