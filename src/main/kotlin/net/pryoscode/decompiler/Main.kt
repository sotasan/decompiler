@file:JvmName("Main")
package net.pryoscode.decompiler

import javafx.application.Platform
import net.pryoscode.decompiler.windows.Window

fun main() {
    Platform.startup {}
    Platform.runLater { Window().show() }
}