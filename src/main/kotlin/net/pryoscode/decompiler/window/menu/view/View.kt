package net.pryoscode.decompiler.window.menu.view

import net.pryoscode.decompiler.window.menu.view.items.ZoomIn
import net.pryoscode.decompiler.window.menu.view.items.ZoomOut
import net.pryoscode.decompiler.window.menu.view.items.ZoomReset
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