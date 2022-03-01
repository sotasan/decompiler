@file:JvmName("Main")
package net.pryoscode.decompiler

import javafx.application.Platform
import net.pryoscode.decompiler.window.Window

fun main(args: Array<String>) {
    Platform.startup { Window(args) }
}