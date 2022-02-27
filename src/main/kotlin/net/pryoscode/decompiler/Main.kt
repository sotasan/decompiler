@file:JvmName("Main")
package net.pryoscode.decompiler

import javafx.application.Platform

fun main() {
    Platform.startup {}
    Platform.runLater { Window() }
}