package dev.shota.decompiler.window.menu

import dev.shota.decompiler.window.menu.edit.Edit
import dev.shota.decompiler.window.menu.file.File
import dev.shota.decompiler.window.menu.help.Help
import dev.shota.decompiler.window.menu.view.View
import javax.swing.JMenuBar

class MenuBar : JMenuBar() {

    init {
        add(File())
        add(Edit())
        add(View())
        add(Help())
    }

}