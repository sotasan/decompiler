package moe.sota.decompiler.jvm.menus.file

import com.formdev.flatlaf.extras.components.FlatMenuItem
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.KeyStroke
import moe.sota.decompiler.jvm.controllers.TabsController
import moe.sota.decompiler.jvm.services.LanguageService

class FileCloseTab(languageService: LanguageService, private val tabsController: TabsController) :
    FlatMenuItem(), ActionListener {

    init {
        accelerator =
            KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        isEnabled = false
        mnemonic = KeyEvent.VK_W
        text = languageService.getString("file.closeTab")

        addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent?) {
        tabsController.closeTab()
    }
}
