package dev.shota.decompiler.window.utils

import dev.shota.decompiler.window.Window
import dev.shota.stylus4j.Stylus
import java.util.*

fun styles(file: String): String {
    val stylus = Window.javaClass.classLoader.getResourceAsStream("styles/$file")
    val css = Stylus.compile(stylus)
    val base64 = Base64.getEncoder().encodeToString((css).toByteArray())
    return "data:text/css;base64,$base64"
}