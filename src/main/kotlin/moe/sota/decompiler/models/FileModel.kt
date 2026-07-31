package moe.sota.decompiler.models

import java.util.jar.JarEntry
import java.util.jar.JarFile
import moe.sota.decompiler.services.TypeService

class FileModel(private val jarFile: JarFile, private val jarEntry: JarEntry) :
    BaseModel(jarEntry.name, false) {
    val type = TypeService.getType(this)

    init {
        loadIcon(type?.icon ?: "icons/file.png")
    }

    val bytes: ByteArray
        get() = jarFile.getInputStream(jarEntry).readBytes()
}
