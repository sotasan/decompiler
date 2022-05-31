package net.pryoscode.decompiler

import com.formdev.flatlaf.FlatLightLaf
import net.pryoscode.decompiler.window.Window

class Main {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            System.setProperty("apple.laf.useScreenMenuBar", "true")
            System.setProperty("apple.awt.application.name", "Decompiler")
            FlatLightLaf.setup()
            Window.run(args)
        }

    }

}