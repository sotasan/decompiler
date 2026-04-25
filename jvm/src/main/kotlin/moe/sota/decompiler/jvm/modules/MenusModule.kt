package moe.sota.decompiler.jvm.modules

import moe.sota.decompiler.jvm.menus.MenuBar
import moe.sota.decompiler.jvm.menus.file.*
import moe.sota.decompiler.jvm.menus.help.Help
import moe.sota.decompiler.jvm.menus.help.HelpAbout
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
