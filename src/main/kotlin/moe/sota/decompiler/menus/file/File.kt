package moe.sota.decompiler.menus.file

import com.formdev.flatlaf.extras.components.FlatMenu
import moe.sota.decompiler.services.LanguageService
import java.awt.event.KeyEvent
import javax.swing.JSeparator

class File(
    openFile: FileOpenFile,
    findFile: FileFind,
    searchFile: FileSearch,
    closeTab: FileCloseTab,
    newInstance: FileNewInstance,
    exit: FileExit
) : FlatMenu() {

    init {
        mnemonic = KeyEvent.VK_F
        text = LanguageService.getTranslation("file")

        add(openFile)
        add(findFile)
        add(searchFile)
        add(closeTab)
        add(JSeparator())
        add(newInstance)
        add(exit)
    }

}
