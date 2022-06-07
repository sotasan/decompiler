package dev.shota.decompiler

import com.formdev.flatlaf.FlatLightLaf
import javafx.application.Platform
import dev.shota.decompiler.window.Window

class Main {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            System.setProperty("apple.laf.useScreenMenuBar", "true")
            System.setProperty("apple.awt.application.name", "Decompiler")
            System.setProperty("apple.awt.application.appearance", "NSAppearanceNameAqua")
            FlatLightLaf.setup()
            Platform.startup {}
            Window.run(args)
        }

    }

}