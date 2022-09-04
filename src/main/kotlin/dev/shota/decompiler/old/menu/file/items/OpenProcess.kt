package dev.shota.decompiler.old.menu.file.items

import dev.shota.decompiler.window.menu.MenuItem
import dev.shota.decompiler.window.dialogs.Processes
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

class OpenProcess : MenuItem("file.openProcess", KeyEvent.VK_O, KeyEvent.SHIFT_DOWN_MASK) {

    init {
        isEnabled = false
    }

    override fun actionPerformed(e: ActionEvent?) {
        Processes()
    }

}