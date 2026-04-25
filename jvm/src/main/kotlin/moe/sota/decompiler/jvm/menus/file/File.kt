package moe.sota.decompiler.jvm.menus.file

import com.formdev.flatlaf.extras.components.FlatMenu
import java.awt.event.KeyEvent
import javax.swing.JSeparator
import moe.sota.decompiler.jvm.services.LanguageService

class File(
    fileOpenFile: FileOpenFile,
    fileCloseTab: FileCloseTab,
    fileNewInstance: FileNewInstance,
    fileExit: FileExit,
    languageService: LanguageService,
) : FlatMenu() {
    init {
        mnemonic = KeyEvent.VK_F
        text = languageService.getString("file")

        add(fileOpenFile)
        add(fileCloseTab)
        add(JSeparator())
        add(fileNewInstance)
        add(fileExit)
    }
}
