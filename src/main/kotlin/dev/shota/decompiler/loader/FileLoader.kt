package dev.shota.decompiler.loader

import dev.shota.decompiler.window.Window
import dev.shota.decompiler.window.container.Code
import dev.shota.decompiler.window.container.Container
import javafx.application.Platform
import java.io.File

fun fileLoader(file: File): Boolean {
    if (!file.extension.equals("jar", true) &&
        !file.extension.equals("war", true) &&
        !file.extension.equals("zip", true) &&
        !file.extension.equals("class", true))
        return false

    Platform.runLater {
        Container.tabs.clear()
        Window.root.items.clear()

        if (file.extension.equals("class", true)) {
            Window.root.items.add(Container)
            val tab = Code(file.name, file.readBytes(), true)
            Container.tabs.add(tab)
        }
    }

    return true
}