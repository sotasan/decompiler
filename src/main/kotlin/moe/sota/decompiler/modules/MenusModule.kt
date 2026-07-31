package moe.sota.decompiler.modules

import moe.sota.decompiler.menus.MenuBar
import moe.sota.decompiler.menus.file.File
import moe.sota.decompiler.menus.file.FileCloseTab
import moe.sota.decompiler.menus.file.FileExit
import moe.sota.decompiler.menus.file.FileNewInstance
import moe.sota.decompiler.menus.file.FileOpenFile
import moe.sota.decompiler.menus.help.Help
import moe.sota.decompiler.menus.help.HelpAbout
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val menusModule = module {
    singleOf(::File)
    singleOf(::FileCloseTab)
    singleOf(::FileExit)
    singleOf(::FileNewInstance)
    singleOf(::FileOpenFile)

    singleOf(::Help)
    singleOf(::HelpAbout)

    singleOf(::MenuBar)
}
