package com.sotasan.decompiler.transformers;

import com.sotasan.decompiler.models.FileModel;
import org.jd.core.v1.ClassFileToJavaSourceDecompiler;
import org.jd.core.v1.api.loader.Loader;
import org.jd.core.v1.api.printer.Printer;
import org.jetbrains.annotations.NotNull;

public class JDTransformer implements ITransformer, Loader, Printer {

    private FileModel fileModel;
    private StringBuilder builder;
    private int indent;

    @Override
    public String transform(@NotNull FileModel fileModel) throws Exception {
        this.fileModel = fileModel;
        builder = new StringBuilder();
        String pkg = fileModel.getPath().substring(0, fileModel.getPath().lastIndexOf('/')).replace('/', '.');
        builder.append(String.format("package %s;\n\n", pkg));
        ClassFileToJavaSourceDecompiler decompiler = new ClassFileToJavaSourceDecompiler();
        decompiler.decompile(this, this, "");
        return builder.toString().trim();
    }

    @Override
    public boolean canLoad(String internalName) {
        return false;
    }

    @Override
    public void endLine() {
        builder.append('\n');
    }

    @Override
    public void extraLine(int count) {
        while (count-- > 0)
            builder.append('\n');
    }

    @Override
    public void indent() {
        indent++;
    }

    @Override
    public byte[] load(String internalName) {
        return fileModel.getBytes();
    }

    @Override
    public void printDeclaration(int type, String internalTypeName, String name, String descriptor) {
        builder.append(name);
    }

    @Override
    public void printKeyword(String keyword) {
        builder.append(keyword);
    }

    @Override
    public void printNumericConstant(String constant) {
        builder.append(constant);
    }

    @Override
    public void printReference(int type, String internalTypeName, String name, String descriptor, String ownerInternalName) {
        builder.append(name);
    }

    @Override
    public void printStringConstant(String constant, String ownerInternalName) {
        builder.append(constant);
    }

    @Override
    public void printText(String text) {
        builder.append(text);
    }

    @Override
    public void startLine(int lineNumber) {
        builder.append("    ".repeat(Math.max(0, indent)));
    }

    @Override
    public void unindent() {
        indent--;
    }

    @Override public void start(int maxLineNumber, int majorVersion, int minorVersion) {}
    @Override public void startMarker(int type) {}
    @Override public void end() {}
    @Override public void endMarker(int type) {}

}