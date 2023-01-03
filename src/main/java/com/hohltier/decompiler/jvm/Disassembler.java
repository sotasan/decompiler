package com.hohltier.decompiler.jvm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.util.TraceClassVisitor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class Disassembler implements IDestructure {

    private final String code;

    public Disassembler(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);
        Writer writer = new StringWriter();
        ClassVisitor visitor = new TraceClassVisitor(new PrintWriter(writer));
        reader.accept(visitor, ClassReader.SKIP_DEBUG);
        code = writer.toString().trim();
    }

    @Override
    public String getCode() {
        return code;
    }

}