package dev.shota.decompiler.old.menu

import dev.shota.decompiler.old.menu.view.items.Language
import javax.swing.JMenu

abstract class Menu(displayText: String, keyCode: Int) : JMenu() {

    init {
        val translation = Language.get(displayText)
        text = translation.value
        translation.addListener { _, _, value -> text = value }
        mnemonic = keyCode
    }

}