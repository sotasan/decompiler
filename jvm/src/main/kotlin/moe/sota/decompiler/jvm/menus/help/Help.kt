package moe.sota.decompiler.jvm.menus.help

import com.formdev.flatlaf.extras.components.FlatMenu
import java.awt.Desktop
import java.awt.event.KeyEvent
import moe.sota.decompiler.jvm.services.LanguageService

class Help(helpAbout: HelpAbout, languageService: LanguageService) : FlatMenu() {
    init {
        isVisible =
            !(Desktop.isDesktopSupported() &&
                Desktop.getDesktop().isSupported(Desktop.Action.APP_ABOUT))
        mnemonic = KeyEvent.VK_H
        text = languageService.getString("help")

        add(helpAbout)
    }
}
