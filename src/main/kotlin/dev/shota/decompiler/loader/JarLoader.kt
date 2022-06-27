package dev.shota.decompiler.loader

import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.util.jar.JarFile

fun jarLoader(file: File) {
    val classes = mapOf<String, ClassNode>()
    val files = mapOf<String, ByteArray>()
    val jar = JarFile(file)

    for (entry in jar.entries()) {
        if (!entry.isDirectory) {
            if (entry.name.endsWith(".class", true)) {
                val bytes = jar.getInputStream(entry).readAllBytes()
                // TODO: cafebabe
                val reader = ClassReader(bytes)
                val node = ClassNode()
                reader.accept(node, ClassReader.EXPAND_FRAMES)
                println(node.name)
            }
        }
    }
}