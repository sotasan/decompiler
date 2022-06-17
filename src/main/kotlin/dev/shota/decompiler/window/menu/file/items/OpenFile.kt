package dev.shota.decompiler.window.menu.file.items

import javafx.application.Platform
import dev.shota.decompiler.window.Window
import dev.shota.decompiler.window.sidebar.Sidebar
import dev.shota.decompiler.window.utils.translate
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JFileChooser
import javax.swing.JMenuItem
import javax.swing.KeyStroke
import javax.swing.filechooser.FileNameExtensionFilter

class OpenFile : JMenuItem("${translate("file.openFile")}...", KeyEvent.VK_O), ActionListener {

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent?) {
        val fileChooser = JFileChooser()
        fileChooser.dialogTitle = text
        fileChooser.fileFilter = FileNameExtensionFilter(translate("file.openFile.archive"), "jar")
        fileChooser.isAcceptAllFileFilterUsed = false
        val option = fileChooser.showOpenDialog(Window)
        if (option == JFileChooser.APPROVE_OPTION)
            Platform.runLater { Sidebar.open(fileChooser.selectedFile) }
    }

}