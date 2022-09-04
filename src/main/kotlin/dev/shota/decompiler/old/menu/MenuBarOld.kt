package dev.shota.decompiler.old.menu

import dev.shota.decompiler.old.menu.edit.Edit
import dev.shota.decompiler.old.menu.file.File
import dev.shota.decompiler.old.menu.help.Help
import dev.shota.decompiler.old.menu.view.View
import javax.swing.JMenuBar

class MenuBarOld : JMenuBar() {

    init {
        add(File())
        add(Edit())
        add(View())
        add(Help())
    }

}