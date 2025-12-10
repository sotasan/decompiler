package moe.sota.decompiler.menus.file

import com.formdev.flatlaf.extras.components.FlatMenu
import moe.sota.decompiler.services.LanguageService
import java.awt.event.KeyEvent
import javax.swing.JSeparator

class File(
    fileOpenFile: FileOpenFile,
    fileCloseTab: FileCloseTab,
    fileNewInstance: FileNewInstance,
    fileExit: FileExit,
    languageService: LanguageService
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
