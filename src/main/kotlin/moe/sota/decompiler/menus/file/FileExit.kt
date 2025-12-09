package moe.sota.decompiler.menus.file

import com.formdev.flatlaf.extras.components.FlatMenuItem
import moe.sota.decompiler.controllers.WindowController
import moe.sota.decompiler.services.LanguageService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.awt.Desktop
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.KeyStroke

class FileExit : FlatMenuItem(), ActionListener, KoinComponent {

    private val windowController: WindowController by inject()

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        isVisible = !(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.APP_QUIT_HANDLER))
        mnemonic = KeyEvent.VK_Q
        text = LanguageService.getTranslation("file.exit")
        addActionListener(this)

        if (!isVisible)
            Desktop.getDesktop().setQuitHandler { _, _ -> actionPerformed(null) }
    }

    override fun actionPerformed(e: ActionEvent?) {
        windowController.dispose()
    }

}
