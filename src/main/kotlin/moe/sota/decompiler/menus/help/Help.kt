package moe.sota.decompiler.menus.help

import com.formdev.flatlaf.extras.components.FlatMenu
import moe.sota.decompiler.services.LanguageService
import java.awt.Desktop
import java.awt.event.KeyEvent

class Help(
    about: HelpAbout
) : FlatMenu() {

    init {
        isVisible = !(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.APP_ABOUT))
        mnemonic = KeyEvent.VK_H
        text = LanguageService.getTranslation("help")

        add(about)
    }

}
