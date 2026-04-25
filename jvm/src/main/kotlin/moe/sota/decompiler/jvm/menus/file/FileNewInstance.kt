package moe.sota.decompiler.jvm.menus.file

import com.formdev.flatlaf.extras.components.FlatMenuItem
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.KeyStroke
import moe.sota.decompiler.jvm.services.LanguageService
import moe.sota.decompiler.jvm.services.ProcessService

class FileNewInstance(
    languageService: LanguageService,
    private val processService: ProcessService,
) : FlatMenuItem(), ActionListener {

    init {
        accelerator =
            KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        mnemonic = KeyEvent.VK_N
        text = languageService.getString("file.newInstance")

        addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent?) {
        processService.start()
    }
}
