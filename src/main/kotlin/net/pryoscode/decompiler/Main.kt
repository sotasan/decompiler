package net.pryoscode.decompiler

import net.pryoscode.decompiler.window.Window
import javax.swing.UIManager

class Main {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            System.setProperty("apple.laf.useScreenMenuBar", "true")
            System.setProperty("apple.awt.application.name", "Decompiler")
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
            Window.run(args)
        }

    }

}