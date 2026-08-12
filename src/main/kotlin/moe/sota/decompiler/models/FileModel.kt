package moe.sota.decompiler.models

import java.util.jar.JarEntry
import java.util.jar.JarFile
import moe.sota.decompiler.services.TypeService
import moe.sota.decompiler.types.Type

class FileModel(private val jarFile: JarFile, private val jarEntry: JarEntry) :
    BaseModel(jarEntry.name, false) {
    val type: Type? = TypeService.getType(this)

    init {
        setIcon(type?.icon ?: "icons/file.png")
    }

    val bytes: ByteArray
        get() = jarFile.getInputStream(jarEntry).readAllBytes()

    val size: Long
        get() = jarEntry.size
}
