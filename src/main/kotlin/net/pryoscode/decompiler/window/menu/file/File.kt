package net.pryoscode.decompiler.window.menu.file

import net.pryoscode.decompiler.window.menu.file.menus.CloseFile
import net.pryoscode.decompiler.window.menu.file.menus.Exit
import net.pryoscode.decompiler.window.menu.file.menus.NewWindow
import net.pryoscode.decompiler.window.menu.file.menus.OpenFile
import java.awt.event.KeyEvent
import javax.swing.JMenu

class File : JMenu("File") {

    init {
        mnemonic = KeyEvent.VK_F
        add(OpenFile())
        add(CloseFile())
        add(NewWindow())
        add(Exit())
    }

}