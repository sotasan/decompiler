package dev.shota.decompiler.window.menu.file.items

import javafx.application.Platform
import dev.shota.decompiler.window.container.Container
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class CloseTab : JMenuItem("Close Tab", KeyEvent.VK_W), ActionListener {

    init {
        isEnabled = false
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        addActionListener(this)

        Container.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            isEnabled = newValue != null
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        Platform.runLater {
            Container.tabs.remove(Container.selectionModel.selectedItem)
        }
    }

}