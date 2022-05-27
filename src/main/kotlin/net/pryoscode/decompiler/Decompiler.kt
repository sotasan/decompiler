package net.pryoscode.decompiler

import net.pryoscode.decompiler.window.Window
import javax.swing.UIManager

class Decompiler {

    companion object {

        @JvmStatic fun main(args: Array<String>) {
            System.setProperty("apple.laf.useScreenMenuBar", "true")
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
            Window.run(args)
        }

    }

}