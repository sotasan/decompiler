package moe.sota.decompiler.jvm.menus

import com.formdev.flatlaf.extras.components.FlatMenuBar
import moe.sota.decompiler.jvm.menus.file.File
import moe.sota.decompiler.jvm.menus.help.Help

class MenuBar(file: File, help: Help) : FlatMenuBar() {
    init {
        add(file)
        add(help)
    }
}
