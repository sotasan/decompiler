package dev.shota.decompiler.window.menu.edit.items

import javafx.application.Platform
import dev.shota.decompiler.window.container.Code
import dev.shota.decompiler.window.container.Container
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class SelectAll : JMenuItem("Select All", KeyEvent.VK_A), ActionListener {

    init {
        isEnabled = false
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        addActionListener(this)

        Container.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            isEnabled = newValue != null
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        Platform.runLater {
            val tab = Container.selectionModel.selectedItem as Code
            tab.codeArea.selectAll()
        }
    }

}