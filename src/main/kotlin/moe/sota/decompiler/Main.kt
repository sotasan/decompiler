package moe.sota.decompiler

import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.FlatLaf
import com.formdev.flatlaf.extras.FlatInspector
import com.formdev.flatlaf.fonts.inter.FlatInterFont
import com.formdev.flatlaf.fonts.jetbrains_mono.FlatJetBrainsMonoFont
import com.formdev.flatlaf.util.SystemInfo
import moe.sota.decompiler.controllers.WindowController
import moe.sota.decompiler.services.LoaderService
import java.io.File

fun main(args: Array<String>) {
    if (SystemInfo.isMacOS) {
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua")
        System.setProperty("apple.awt.application.name", "Decompiler")
        System.setProperty("apple.laf.useScreenMenuBar", "${true}")
    }

    FlatInterFont.install()
    FlatJetBrainsMonoFont.install()
    FlatLaf.setPreferredFontFamily(FlatInterFont.FAMILY)
    FlatLaf.registerCustomDefaultsSource("themes")
    FlatDarkLaf.setup()
    FlatInspector.install("${if (SystemInfo.isMacOS) "meta" else "ctrl"} shift I")

    WindowController.show()
    if (args.isNotEmpty())
        LoaderService.loadAsync(File(args.first()))
}
