package net.pryoscode.decompiler.window.menu.help

import net.pryoscode.decompiler.window.menu.help.items.About
import java.awt.event.KeyEvent
import javax.swing.JMenu

class Help : JMenu("Help") {

    init {
        mnemonic = KeyEvent.VK_H
        add(About())
    }

}