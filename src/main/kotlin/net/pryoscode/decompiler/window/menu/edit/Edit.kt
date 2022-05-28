package net.pryoscode.decompiler.window.menu.edit

import net.pryoscode.decompiler.window.menu.edit.menus.Copy
import net.pryoscode.decompiler.window.menu.edit.menus.Find
import net.pryoscode.decompiler.window.menu.edit.menus.SelectAll
import java.awt.event.KeyEvent
import javax.swing.JMenu

class Edit : JMenu("Edit") {

    init {
        mnemonic = KeyEvent.VK_E
        add(Copy())
        add(SelectAll())
        add(Find())
    }

}