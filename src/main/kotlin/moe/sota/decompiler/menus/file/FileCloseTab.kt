package moe.sota.decompiler.menus.file

import com.formdev.flatlaf.extras.components.FlatMenuItem
import moe.sota.decompiler.controllers.TabsController
import moe.sota.decompiler.services.LanguageService
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.KeyStroke

class FileCloseTab : FlatMenuItem(), ActionListener {

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        isEnabled = false
        mnemonic = KeyEvent.VK_W
        text = LanguageService.getTranslation("file.closeTab")
        addActionListener(this)
    }

    override fun actionPerformed(p0: ActionEvent?) {
        TabsController.INSTANCE.closeTab()
    }

}
