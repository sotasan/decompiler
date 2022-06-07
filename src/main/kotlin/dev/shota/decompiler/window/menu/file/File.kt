package dev.shota.decompiler.window.menu.file

import dev.shota.decompiler.window.menu.file.items.CloseTab
import dev.shota.decompiler.window.menu.file.items.Exit
import dev.shota.decompiler.window.menu.file.items.NewWindow
import dev.shota.decompiler.window.menu.file.items.OpenFile
import java.awt.event.KeyEvent
import javax.swing.JMenu
import javax.swing.JSeparator

class File : JMenu("File") {

    init {
        mnemonic = KeyEvent.VK_F
        add(OpenFile())
        add(CloseTab())
        add(JSeparator())
        add(NewWindow())
        add(Exit())
    }

}