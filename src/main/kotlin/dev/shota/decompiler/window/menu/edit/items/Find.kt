package dev.shota.decompiler.window.menu.edit.items

import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class Find : JMenuItem("Find", KeyEvent.VK_F), ActionListener {

    init {
        isEnabled = false
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_F, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent?) {}

}