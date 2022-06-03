package net.pryoscode.decompiler.window.menu.file.items

import javafx.application.Platform
import net.pryoscode.decompiler.window.Window
import net.pryoscode.decompiler.window.sidebar.Sidebar
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JFileChooser
import javax.swing.JMenuItem
import javax.swing.KeyStroke
import javax.swing.filechooser.FileNameExtensionFilter

class OpenArchive : JMenuItem("Open Archive", KeyEvent.VK_O), ActionListener {

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent?) {
        val fileChooser = JFileChooser()
        fileChooser.dialogTitle = text
        fileChooser.fileFilter = FileNameExtensionFilter("Java Archive", "jar")
        fileChooser.isAcceptAllFileFilterUsed = false
        val option = fileChooser.showOpenDialog(Window)
        if (option == JFileChooser.APPROVE_OPTION)
            Platform.runLater { Sidebar.open(fileChooser.selectedFile) }
    }

}