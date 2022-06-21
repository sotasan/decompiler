package dev.shota.decompiler.window.menu.file.items

import javafx.application.Platform
import dev.shota.decompiler.window.Window
import dev.shota.decompiler.window.menu.MenuItem
import dev.shota.decompiler.window.menu.view.items.Language
import dev.shota.decompiler.window.sidebar.Sidebar
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class OpenFile : MenuItem("file.openFile", KeyEvent.VK_O) {

    override fun actionPerformed(e: ActionEvent?) {
        val fileChooser = JFileChooser()
        fileChooser.dialogTitle = text
        fileChooser.fileFilter = FileNameExtensionFilter(Language.get("file.openFile.archive").value, "jar")
        fileChooser.isAcceptAllFileFilterUsed = false
        val option = fileChooser.showOpenDialog(Window)
        if (option == JFileChooser.APPROVE_OPTION)
            Platform.runLater { Sidebar.open(fileChooser.selectedFile) }
    }

}