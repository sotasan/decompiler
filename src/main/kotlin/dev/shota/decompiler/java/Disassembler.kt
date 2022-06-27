package dev.shota.decompiler.java

import org.objectweb.asm.ClassReader
import org.objectweb.asm.util.TraceClassVisitor
import java.io.ByteArrayInputStream
import java.io.PrintWriter
import java.io.StringWriter

class Disassembler(data: ByteArray) {

    val code: String

    init {
        val reader = ClassReader(ByteArrayInputStream(data))
        val writer = StringWriter()
        val visitor = TraceClassVisitor(PrintWriter(writer))
        reader.accept(visitor, ClassReader.SKIP_DEBUG)
        code = writer.toString().trimEnd()
    }

}