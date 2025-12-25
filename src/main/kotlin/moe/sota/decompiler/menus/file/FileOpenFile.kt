package moe.sota.decompiler.menus.file

import com.formdev.flatlaf.extras.components.FlatMenuItem
import moe.sota.decompiler.services.LanguageService
import moe.sota.decompiler.services.LoaderService
import moe.sota.decompiler.views.WindowView
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JFileChooser
import javax.swing.KeyStroke
import javax.swing.filechooser.FileNameExtensionFilter

class FileOpenFile(
    languageService: LanguageService
) : FlatMenuItem(), ActionListener, KoinComponent {

    private val windowView: WindowView by inject()
    private val originalText = languageService.getString("file.openFile")

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        mnemonic = KeyEvent.VK_O
        text = "$originalText..."

        addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent?) {
        val fileChooser = JFileChooser().apply {
            isAcceptAllFileFilterUsed = false
            dialogTitle = originalText
            fileFilter = FileNameExtensionFilter("Java (*.jar;*.war;*.zip)", "jar", "war", "zip")
        }

        fileChooser.showOpenDialog(windowView)
        if (fileChooser.selectedFile != null)
            LoaderService.loadAsync(fileChooser.selectedFile)
    }

}
