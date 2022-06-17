package dev.shota.decompiler.window.utils

import java.util.*

private val bundle = ResourceBundle.getBundle("language", Locale.getDefault())

fun language(key: String): String {
    return bundle.getString(key)
}