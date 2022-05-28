package net.pryoscode.decompiler.window.menu.view

import net.pryoscode.decompiler.window.menu.view.menus.ZoomIn
import net.pryoscode.decompiler.window.menu.view.menus.ZoomOut
import net.pryoscode.decompiler.window.menu.view.menus.ZoomReset
import java.awt.event.KeyEvent
import javax.swing.JMenu

class View : JMenu("View") {

    init {
        mnemonic = KeyEvent.VK_V
        add(ZoomIn())
        add(ZoomOut())
        add(ZoomReset())
    }

}