package dev.shota.decompiler.old.utils

import dev.shota.decompiler.Main

private val data = HashMap<String, ByteArray>()

fun assets(file: String): ByteArray {
    if (data.contains(file))
        return data[file]!!

    val bytes = Main::class.java.classLoader.getResourceAsStream(file)?.readAllBytes()!!
    data[file] = bytes
    return bytes
}