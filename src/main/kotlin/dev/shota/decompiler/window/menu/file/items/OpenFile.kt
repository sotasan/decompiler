package dev.shota.decompiler.window.menu.file.items

import dev.shota.decompiler.window.Window
import dev.shota.decompiler.window.menu.MenuItem
import dev.shota.decompiler.window.sidebar.Sidebar
import javafx.application.Platform
import java.awt.FileDialog
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.io.File

class OpenFile : MenuItem("file.openFile", KeyEvent.VK_O) {

    override fun actionPerformed(e: ActionEvent?) {
        val fileDialog = FileDialog(Window, text)
        fileDialog.setFilenameFilter { dir, name ->
            val extension = File(dir, name).extension
            extension.equals("jar", true) ||
            extension.equals("war", true) ||
            extension.equals("zip", true)
        }
        fileDialog.isVisible = true
        if (fileDialog.directory != null && fileDialog.file != null)
            Platform.runLater { Sidebar.open(File(fileDialog.directory, fileDialog.file)) }
    }

}