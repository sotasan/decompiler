package net.pryoscode.decompiler.window.menu.file.items

import javafx.application.Platform
import net.pryoscode.decompiler.window.Window
import net.pryoscode.decompiler.window.sidebar.Sidebar
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.JFileChooser
import javax.swing.JMenuItem
import javax.swing.KeyStroke
import javax.swing.filechooser.FileNameExtensionFilter

class OpenFile : JMenuItem("Open File", KeyEvent.VK_O) {

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK)
        addActionListener(::actionListener)
    }

    private fun actionListener(event: ActionEvent) {
        val fileChooser = JFileChooser()
        fileChooser.dialogTitle = text
        fileChooser.fileFilter = FileNameExtensionFilter("Java Archive", "jar")
        fileChooser.isAcceptAllFileFilterUsed = false
        val option = fileChooser.showOpenDialog(Window)
        if (option == JFileChooser.APPROVE_OPTION)
            Platform.runLater { Sidebar.open(fileChooser.selectedFile) }
    }

}