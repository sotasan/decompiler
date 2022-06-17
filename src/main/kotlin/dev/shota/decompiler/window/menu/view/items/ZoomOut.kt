package dev.shota.decompiler.window.menu.view.items

import dev.shota.decompiler.window.container.Code
import dev.shota.decompiler.window.container.Container
import dev.shota.decompiler.window.utils.translate
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class ZoomOut : JMenuItem(translate("view.zoomOut"), KeyEvent.VK_MINUS), ActionListener {

    init {
        isEnabled = false
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        addActionListener(this)

        Container.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            isEnabled = newValue != null
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        Code.changeZoom(-0.25)
    }

}