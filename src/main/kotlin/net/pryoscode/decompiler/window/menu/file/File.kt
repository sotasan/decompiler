package net.pryoscode.decompiler.window.menu.file

import net.pryoscode.decompiler.window.menu.file.items.CloseTab
import net.pryoscode.decompiler.window.menu.file.items.Exit
import net.pryoscode.decompiler.window.menu.file.items.NewWindow
import net.pryoscode.decompiler.window.menu.file.items.OpenFile
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