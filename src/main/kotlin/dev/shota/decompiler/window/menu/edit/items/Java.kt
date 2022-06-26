package dev.shota.decompiler.window.menu.edit.items

import dev.shota.decompiler.java.Decompiler
import dev.shota.decompiler.window.container.Code
import dev.shota.decompiler.window.container.Container
import dev.shota.decompiler.window.menu.edit.Edit
import dev.shota.decompiler.window.menu.view.items.Language
import dev.shota.decompiler.window.sidebar.Type
import javafx.application.Platform
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JRadioButtonMenuItem
import javax.swing.KeyStroke

class Java : JRadioButtonMenuItem(), ActionListener {

    init {
        Edit.group.add(this)
        isEnabled = false
        Container.selectionModel.selectedItemProperty().addListener { _, _, item ->
            if (item == null) {
                isEnabled = false
                Edit.group.clearSelection()
                return@addListener
            }

            val code = item as Code
            if (code.entry.type == Type.CLASS) {
                isEnabled = true
                isSelected = code.type == Code.CodeType.JAVA
            } else {
                isEnabled = false
                Edit.group.clearSelection()
            }
        }

        val translation = Language.get("edit.java")
        text = translation.value
        translation.addListener { _, _, value -> text = value }
        mnemonic = KeyEvent.VK_J
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_J, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        addActionListener(::actionPerformed)
    }

    override fun actionPerformed(e: ActionEvent?) {
        Platform.runLater {
            val code = Container.selectionModel.selectedItem as Code
            code.type = Code.CodeType.JAVA
            code.codeArea.replaceText(Decompiler(code.data).code)
        }
    }

}