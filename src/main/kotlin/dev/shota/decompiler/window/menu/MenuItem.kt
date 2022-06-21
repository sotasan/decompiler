package dev.shota.decompiler.window.menu

import dev.shota.decompiler.window.menu.view.items.Language
import java.awt.Toolkit
import java.awt.event.ActionListener
import javax.swing.JMenuItem
import javax.swing.KeyStroke

abstract class MenuItem(displayText: String, keyCode: Int, modifiers: Int = 0, keyStroke: Boolean = true) : JMenuItem(), ActionListener {

    init {
        val translation = Language.get(displayText)
        text = translation.value
        translation.addListener { _, _, value -> text = value }
        mnemonic = keyCode
        if (keyStroke) accelerator = KeyStroke.getKeyStroke(keyCode, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx + modifiers)
        addActionListener(::actionPerformed)
    }

}