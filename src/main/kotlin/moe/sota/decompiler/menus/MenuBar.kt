package moe.sota.decompiler.menus

import com.formdev.flatlaf.extras.components.FlatMenuBar
import moe.sota.decompiler.menus.file.File
import moe.sota.decompiler.menus.help.Help

class MenuBar(
    file: File,
    help: Help
) : FlatMenuBar() {

    init {
        add(file)
        add(help)
    }

}
