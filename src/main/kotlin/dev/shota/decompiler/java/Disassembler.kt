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
        val tcv = TraceClassVisitor(PrintWriter(writer))
        reader.accept(tcv, 0)
        code = writer.toString()
    }

}