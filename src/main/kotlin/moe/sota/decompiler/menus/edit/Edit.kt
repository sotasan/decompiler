package moe.sota.decompiler.menus.edit

import com.formdev.flatlaf.extras.components.FlatMenu
import java.awt.event.KeyEvent
import moe.sota.decompiler.services.LanguageService

class Edit(editFind: EditFind, languageService: LanguageService) : FlatMenu() {
    init {
        mnemonic = KeyEvent.VK_E
        text = languageService.getString("edit")

        add(editFind)
    }
}
