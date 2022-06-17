package dev.shota.decompiler.window.menu.view.items

import dev.shota.decompiler.window.utils.language
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class ZoomOut : JMenuItem(language("view.zoomOut"), KeyEvent.VK_MINUS), ActionListener {

    init {
        isEnabled = false
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent?) {}

}