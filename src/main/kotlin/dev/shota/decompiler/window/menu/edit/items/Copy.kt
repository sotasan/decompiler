package dev.shota.decompiler.window.menu.edit.items

import dev.shota.decompiler.window.container.Code
import dev.shota.decompiler.window.container.Container
import dev.shota.decompiler.window.utils.language
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class Copy : JMenuItem(language("edit.copy"), KeyEvent.VK_C), ActionListener {

    init {
        isEnabled = false
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        addActionListener(this)

        Container.selectionModel.selectedItemProperty().addListener { _, _, item ->
            if (item == null) {
                isEnabled = false
                return@addListener
            }

            val tab = (item as Code)
            isEnabled = tab.codeArea.selectedText.isNotEmpty()
            tab.codeArea.selectedTextProperty().addListener { _, _, text ->
                isEnabled = text.isNotEmpty()
            }
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        val tab = Container.selectionModel.selectedItem as Code
        val text = tab.codeArea.selectedText
        val content = StringSelection(text)
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(content, content)
    }

}