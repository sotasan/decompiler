package dev.shota.decompiler.old.menu.file.items

import com.formdev.flatlaf.util.SystemInfo
import dev.shota.decompiler.old.WindowOld
import dev.shota.decompiler.old.menu.MenuItem
import dev.shota.decompiler.old.menu.view.items.Language
import java.awt.FileDialog
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.io.File
import javax.swing.JFileChooser
import javax.swing.SwingUtilities
import javax.swing.filechooser.FileNameExtensionFilter

class OpenFile : MenuItem("file.openFile", KeyEvent.VK_O) {

    override fun actionPerformed(e: ActionEvent?) {
        SwingUtilities.invokeLater {
            val f: File?

            if (SystemInfo.isMacOS) {
                f = FileDialog(WindowOld, text).run {
                    setFilenameFilter { dir, name ->
                        val extension = File(dir, name).extension
                        extension.equals("jar", true) ||
                        extension.equals("war", true) ||
                        extension.equals("zip", true)
                    }
                    isVisible = true
                    if (directory != null && file != null) File(directory, file) else null
                }

            } else {
                f = JFileChooser().run {
                    dialogTitle = text
                    fileFilter = FileNameExtensionFilter(Language.get("file.openFile.archive").value, "jar", "war", "zip")
                    isAcceptAllFileFilterUsed = false
                    val option = showOpenDialog(WindowOld)
                    if (option == JFileChooser.APPROVE_OPTION) selectedFile else null
                }
            }

            /*
            if (f != null)
                FileLoader.load(f)
             */
        }
    }

}