package net.pryoscode.decompiler.window.sidebar

import java.util.jar.JarEntry
import java.util.jar.JarFile

class Entry(val file: JarFile?, val entry: JarEntry?) {

    lateinit var name: String
    lateinit var path: String
    lateinit var type: Type

    init {
        if (entry != null) {
            val base = if (entry.isDirectory) entry.name.substringBeforeLast("/") else entry.name
            name = base.substringAfterLast("/")
            path = entry.name
            type = if (entry.isDirectory) Type.PACKAGE else if (entry.name.endsWith(".class", true)) Type.CLASS else Type.FILE
        }
    }

}