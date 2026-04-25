@file:JvmName("Main")

package moe.sota.decompiler.jvm

import com.formdev.flatlaf.util.SystemInfo
import moe.sota.decompiler.jvm.modules.controllersModule
import moe.sota.decompiler.jvm.modules.menusModule
import moe.sota.decompiler.jvm.modules.servicesModule
import moe.sota.decompiler.jvm.modules.viewsModule
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    if (SystemInfo.isMacOS) {
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua")
        System.setProperty("apple.awt.application.name", "Decompiler")
        System.setProperty("apple.laf.useScreenMenuBar", "${true}")
    }

    startKoin { modules(controllersModule, menusModule, servicesModule, viewsModule) }

    Application.run(args)
}
