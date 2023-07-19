package com.hohltier.decompiler.transformers;

import com.hohltier.decompiler.models.FileModel;
import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences;
import org.jetbrains.java.decompiler.main.extern.IResultSaver;
import java.io.File;
import java.util.jar.Manifest;

public class FernflowerTransformer extends IFernflowerLogger implements ITransformer, IBytecodeProvider, IResultSaver {

    private FileModel fileModel;
    private String content;

    @Override
    public String transform(FileModel fileModel) {
        this.fileModel = fileModel;
        // TODO: Refactor
        Fernflower fernflower = new Fernflower(this, this, IFernflowerPreferences.getDefaults(), this);
        fernflower.addSource(new File(".class"));
        fernflower.decompileContext();
        return content.trim();
    }

    @Override
    public byte[] getBytecode(String externalPath, String internalPath) {
        return fileModel.getBytes();
    }

    @Override
    public void saveClassFile(String path, String qualifiedName, String entryName, String content, int[] mapping) {
        this.content = content;
    }

    @Override public void closeArchive(String path, String archiveName) {}
    @Override public void copyEntry(String source, String path, String archiveName, String entry) {}
    @Override public void copyFile(String source, String path, String entryName) {}
    @Override public void createArchive(String path, String archiveName, Manifest manifest) {}
    @Override public void saveClassEntry(String path, String archiveName, String qualifiedName, String entryName, String content) {}
    @Override public void saveDirEntry(String path, String archiveName, String entryName) {}
    @Override public void saveFolder(String path) {}
    @Override public void writeMessage(String message, Severity severity) {}
    @Override public void writeMessage(String message, Severity severity, Throwable t) {}

}