package dev.shota.decompiler

import com.formdev.flatlaf.FlatLightLaf
import javafx.application.Platform
import dev.shota.decompiler.window.Window
import dev.shota.decompiler.window.sidebar.Sidebar
import java.io.File

class Main {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            System.setProperty("apple.laf.useScreenMenuBar", "true")
            System.setProperty("apple.awt.application.name", "Decompiler")
            System.setProperty("apple.awt.application.appearance", "NSAppearanceNameAqua")
            FlatLightLaf.setup()
            Platform.startup {}
            Window.isVisible = true
            if (args.isNotEmpty()) Platform.runLater { Sidebar.open(File(args[0])) }
        }

    }

}