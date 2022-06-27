package dev.shota.decompiler.window.menu.file.items

import com.formdev.flatlaf.util.SystemInfo
import dev.shota.decompiler.window.Window
import dev.shota.decompiler.window.menu.MenuItem
import dev.shota.decompiler.window.menu.view.items.Language
import dev.shota.decompiler.window.sidebar.Sidebar
import javafx.application.Platform
import java.awt.FileDialog
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class OpenFile : MenuItem("file.openFile", KeyEvent.VK_O) {

    override fun actionPerformed(e: ActionEvent?) {
        var file: File? = null

        if (SystemInfo.isMacOS) {

            val fileDialog = FileDialog(Window, text)
            fileDialog.setFilenameFilter { dir, name ->
                val extension = File(dir, name).extension
                extension.equals("jar", true) ||
                extension.equals("war", true) ||
                extension.equals("zip", true)
            }
            fileDialog.isVisible = true
            if (fileDialog.directory != null && fileDialog.file != null)
                file = File(fileDialog.directory, fileDialog.file)

        } else {

            val fileChooser = JFileChooser()
            fileChooser.dialogTitle = text
            fileChooser.fileFilter = FileNameExtensionFilter(Language.get("file.openFile.archive").value, "jar", "war", "zip")
            fileChooser.isAcceptAllFileFilterUsed = false
            val option = fileChooser.showOpenDialog(Window)
            if (option == JFileChooser.APPROVE_OPTION)
                file = fileChooser.selectedFile

        }

        if (file != null)
            Platform.runLater { Sidebar.open(file) }
    }

}