package dev.shota.decompiler.loader

import dev.shota.decompiler.window.container.Code
import dev.shota.decompiler.window.container.Container
import javafx.application.Platform
import java.io.File
import java.util.jar.JarFile

fun fileLoader(file: File): Boolean {
    if (!file.extension.equals("jar", true) &&
        !file.extension.equals("war", true) &&
        !file.extension.equals("zip", true) &&
        !file.extension.equals("class", true))
        return false

    Platform.runLater {

        if (file.extension.equals("class", true)) {
            val tab = Code(file.name, file.readBytes(), true)
            if (!Container.tabs.contains(tab))
                Container.tabs.add(tab)
            return@runLater
        }

        Container.tabs.clear()
        val jar = JarFile(file)
        for (entry in jar.entries())
            println(entry.name)

    }
    return true
}