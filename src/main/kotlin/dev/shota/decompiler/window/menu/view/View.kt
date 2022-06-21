package dev.shota.decompiler.window.menu.view

import dev.shota.decompiler.window.menu.Menu
import dev.shota.decompiler.window.menu.view.items.Language
import dev.shota.decompiler.window.menu.view.items.ZoomIn
import dev.shota.decompiler.window.menu.view.items.ZoomOut
import dev.shota.decompiler.window.menu.view.items.ZoomReset
import java.awt.event.KeyEvent
import javax.swing.JSeparator

class View : Menu("view", KeyEvent.VK_V) {

    init {
        add(ZoomIn())
        add(ZoomOut())
        add(ZoomReset())
        add(JSeparator())
        add(Language)
    }

}