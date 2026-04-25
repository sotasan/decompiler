package moe.sota.decompiler.jvm.menus.help

import com.formdev.flatlaf.extras.components.FlatMenuItem
import java.awt.Desktop
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import moe.sota.decompiler.jvm.controllers.AboutController
import moe.sota.decompiler.jvm.services.LanguageService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HelpAbout(languageService: LanguageService) : FlatMenuItem(), ActionListener, KoinComponent {
    private val aboutController: AboutController by inject()

    init {
        mnemonic = KeyEvent.VK_A
        text = languageService.getString("about")

        addActionListener(this)

        if (
            Desktop.isDesktopSupported() &&
                Desktop.getDesktop().isSupported(Desktop.Action.APP_ABOUT)
        )
            Desktop.getDesktop().setAboutHandler { actionPerformed(null) }
    }

    override fun actionPerformed(e: ActionEvent?) {
        aboutController.show()
    }
}
