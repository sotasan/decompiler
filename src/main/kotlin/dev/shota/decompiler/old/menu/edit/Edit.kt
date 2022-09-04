package dev.shota.decompiler.old.menu.edit

import dev.shota.decompiler.old.menu.Menu
import dev.shota.decompiler.old.menu.edit.items.*
import java.awt.event.KeyEvent
import javax.swing.ButtonGroup
import javax.swing.JSeparator

class Edit : Menu("edit", KeyEvent.VK_E) {

    companion object {
        val group = ButtonGroup()
    }

    init {
        add(Copy())
        add(SelectAll())
        add(Find())
        add(JSeparator())
        add(Java())
        add(Bytecode())
    }

}