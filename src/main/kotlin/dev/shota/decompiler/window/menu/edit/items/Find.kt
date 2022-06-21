package dev.shota.decompiler.window.menu.edit.items

import dev.shota.decompiler.window.menu.MenuItem
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

class Find : MenuItem("edit.find", KeyEvent.VK_F) {

    init {
        isEnabled = false
    }

    override fun actionPerformed(e: ActionEvent?) {}

}