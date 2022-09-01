package dev.shota.decompiler.window.utils

import java.util.*

fun styles(file: String): String {
    val css = assets("styles/$file.css")
    val base64 = Base64.getEncoder().encodeToString(css)
    return "data:text/css;base64,$base64"
}