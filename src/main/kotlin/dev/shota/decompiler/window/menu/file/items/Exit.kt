package dev.shota.decompiler.window.menu.file.items

import dev.shota.decompiler.window.Window
import java.awt.Desktop
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class Exit : JMenuItem("Exit", KeyEvent.VK_Q), ActionListener {

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        addActionListener(this)

        if (Desktop.getDesktop().isSupported(Desktop.Action.APP_QUIT_HANDLER)) {
            Desktop.getDesktop().setQuitHandler { _, _ -> actionPerformed(null) }
            isVisible = false
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        Window.dispose()
    }

}