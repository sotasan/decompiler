package moe.sota.decompiler

import com.formdev.flatlaf.util.SystemInfo
import moe.sota.decompiler.modules.controllerModule
import moe.sota.decompiler.modules.menuModule
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    if (SystemInfo.isMacOS) {
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua")
        System.setProperty("apple.awt.application.name", "Decompiler")
        System.setProperty("apple.laf.useScreenMenuBar", "${true}")
    }

    startKoin {
        modules(controllerModule, menuModule)
    }

    Application.start(args)
}
