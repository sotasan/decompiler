package dev.shota.decompiler.old.menu.file

import dev.shota.decompiler.old.menu.Menu
import dev.shota.decompiler.old.menu.file.items.*
import java.awt.event.KeyEvent
import javax.swing.JSeparator

class File : Menu("file", KeyEvent.VK_F) {

    init {
        add(OpenFile())
        add(OpenProcess())
        add(CloseTab())
        add(JSeparator())
        add(NewInstance())
        add(Exit())
    }

}