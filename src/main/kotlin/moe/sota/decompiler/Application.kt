package moe.sota.decompiler

import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.FlatLaf
import com.formdev.flatlaf.extras.FlatInspector
import com.formdev.flatlaf.fonts.inter.FlatInterFont
import com.formdev.flatlaf.fonts.jetbrains_mono.FlatJetBrainsMonoFont
import com.formdev.flatlaf.util.SystemInfo
import moe.sota.decompiler.controllers.WindowController
import moe.sota.decompiler.services.LoaderService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

object Application : KoinComponent {

    private val windowController: WindowController by inject()

    fun start(args: Array<String>) {
        FlatInterFont.install()
        FlatJetBrainsMonoFont.install()
        FlatLaf.setPreferredFontFamily(FlatInterFont.FAMILY)
        FlatLaf.registerCustomDefaultsSource("themes")
        FlatDarkLaf.setup()
        FlatInspector.install("${if (SystemInfo.isMacOS) "meta" else "ctrl"} shift I")

        windowController.show()

        if (args.isNotEmpty())
            LoaderService.loadAsync(File(args.first()))
    }

}
