package dev.shota.decompiler.old.menu.edit.items

import dev.shota.decompiler.old.container.Code
import dev.shota.decompiler.old.container.Container
import dev.shota.decompiler.old.menu.MenuItem
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

class Copy : MenuItem("edit.copy", KeyEvent.VK_C) {

    init {
        isEnabled = false
        Container.selectionModel.selectedItemProperty().addListener { _, _, item ->
            if (item == null) {
                isEnabled = false
                return@addListener
            }

            val tab = (item as Code).apply {
                codeArea.selectedTextProperty().addListener { _, _, text ->
                    isEnabled = text.isNotEmpty()
                }
            }
            isEnabled = tab.codeArea.selectedText.isNotEmpty()
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