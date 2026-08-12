package moe.sota.decompiler.menus.edit

import com.formdev.flatlaf.extras.components.FlatMenuItem
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import javax.swing.KeyStroke
import moe.sota.decompiler.controllers.SearchController
import moe.sota.decompiler.services.LanguageService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EditFind(languageService: LanguageService) : FlatMenuItem(), ActionListener, KoinComponent {
    private val searchController: SearchController by inject()

    init {
        accelerator =
            KeyStroke.getKeyStroke(
                KeyEvent.VK_F,
                Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx or InputEvent.SHIFT_DOWN_MASK,
            )
        isEnabled = false
        mnemonic = KeyEvent.VK_F
        text = "${languageService.getString("edit.find")}..."

        addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent?) {
        searchController.show()
    }
}
