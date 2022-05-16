@file:JvmName("Decompiler")
package net.pryoscode.decompiler

import net.pryoscode.decompiler.window.Window
import javax.swing.UIManager

fun main(args: Array<String>) {
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    Window.run(args)
}