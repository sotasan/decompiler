package dev.shota.decompiler.window.menu.file

import dev.shota.decompiler.window.menu.Menu
import dev.shota.decompiler.window.menu.file.items.*
import java.awt.event.KeyEvent
import javax.swing.JSeparator

class File : Menu("file", KeyEvent.VK_F) {

    init {
        add(OpenFile())
        add(OpenProcess())
        add(CloseTab())
        add(JSeparator())
        add(NewWindow())
        add(Exit())
    }

}