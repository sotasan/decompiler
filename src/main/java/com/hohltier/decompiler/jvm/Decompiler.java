package com.hohltier.decompiler.jvm;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences;
import org.jetbrains.java.decompiler.main.extern.IResultSaver;
import java.io.File;
import java.util.jar.Manifest;

public class Decompiler extends IFernflowerLogger implements IDestructure, IBytecodeProvider, IResultSaver {

    private final byte[] bytes;
    private String code;

    public Decompiler(byte[] bytes) {
        this.bytes = bytes;

        Fernflower fernflower = new Fernflower(this, this, IFernflowerPreferences.getDefaults(), this);
        fernflower.addSource(new File(".class"));

        fernflower.decompileContext();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public byte[] getBytecode(String externalPath, String internalPath) {
        return bytes;
    }

    @Override
    public void saveClassFile(String path, String qualifiedName, String entryName, @NotNull String content, int[] mapping) {
        code = content.trim();
    }

    @Override
    public void writeMessage(String message, Severity severity) {}
    @Override
    public void writeMessage(String message, Severity severity, Throwable t) {}
    @Override
    public void saveFolder(String path) {}
    @Override
    public void copyFile(String source, String path, String entryName) {}
    @Override
    public void createArchive(String path, String archiveName, Manifest manifest) {}
    @Override
    public void saveDirEntry(String path, String archiveName, String entryName) {}
    @Override
    public void copyEntry(String source, String path, String archiveName, String entry) {}
    @Override
    public void saveClassEntry(String path, String archiveName, String qualifiedName, String entryName, String content) {}
    @Override
    public void closeArchive(String path, String archiveName) {}

}