package dev.shota.decompiler.old.menu.edit.items

import dev.shota.decompiler.jvm.Decompiler
import dev.shota.decompiler.old.container.Code
import dev.shota.decompiler.old.container.CodeType
import dev.shota.decompiler.old.container.Container
import dev.shota.decompiler.old.menu.edit.Edit
import dev.shota.decompiler.old.menu.view.items.Language
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
            if (code.clazz) {
                isEnabled = true
                isSelected = code.type == CodeType.JAVA
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
            (Container.selectionModel.selectedItem as Code).run {
                type = CodeType.JAVA
                codeArea.clear()
                codeArea.replaceText(Decompiler(data).code)
                codeArea.moveTo(0)
                codeArea.requestFollowCaret()
            }
        }
    }

}