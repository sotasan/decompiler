package dev.shota.decompiler.old.menu.file.items

import dev.shota.decompiler.old.menu.MenuItem
import dev.shota.decompiler.old.dialogs.Processes
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