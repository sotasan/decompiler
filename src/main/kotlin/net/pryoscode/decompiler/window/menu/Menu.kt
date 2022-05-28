package net.pryoscode.decompiler.window.menu

import net.pryoscode.decompiler.window.menu.edit.Edit
import net.pryoscode.decompiler.window.menu.file.File
import net.pryoscode.decompiler.window.menu.help.Help
import net.pryoscode.decompiler.window.menu.view.View
import javax.swing.JMenuBar

class Menu : JMenuBar() {

    init {
        add(File())
        add(Edit())
        add(View())
        add(Help())
    }

}