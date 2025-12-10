package moe.sota.decompiler.modules

import moe.sota.decompiler.menus.MenuBar
import moe.sota.decompiler.menus.file.*
import moe.sota.decompiler.menus.help.Help
import moe.sota.decompiler.menus.help.HelpAbout
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val menuModule = module {
    singleOf(::File)
    single { FileFind() }
    single { FileSearch() }
    singleOf(::FileCloseTab)
    singleOf(::FileExit)
    singleOf(::FileNewInstance)
    singleOf(::FileOpenFile)

    singleOf(::Help)
    singleOf(::HelpAbout)

    singleOf(::MenuBar)
}
