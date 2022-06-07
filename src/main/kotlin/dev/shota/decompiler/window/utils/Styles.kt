package dev.shota.decompiler.window.utils

import com.github.sommeri.less4j.core.ThreadUnsafeLessCompiler
import dev.shota.decompiler.window.Window
import java.util.*

fun styles(file: String): String {
    val less = String(Window.javaClass.classLoader.getResourceAsStream("styles/$file").readAllBytes())
    val css = ThreadUnsafeLessCompiler().compile(less).css
    val base64 = Base64.getEncoder().encodeToString((css).toByteArray())
    return "data:text/css;base64,$base64"
}