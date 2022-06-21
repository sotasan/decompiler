package dev.shota.decompiler.window.menu.edit

import dev.shota.decompiler.window.menu.Menu
import dev.shota.decompiler.window.menu.edit.items.Copy
import dev.shota.decompiler.window.menu.edit.items.Find
import dev.shota.decompiler.window.menu.edit.items.SelectAll
import java.awt.event.KeyEvent

class Edit : Menu("edit", KeyEvent.VK_E) {

    init {
        add(Copy())
        add(SelectAll())
        add(Find())
    }

}