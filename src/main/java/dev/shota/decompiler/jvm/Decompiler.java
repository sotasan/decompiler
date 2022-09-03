package dev.shota.decompiler.jvm;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences;
import org.jetbrains.java.decompiler.main.extern.IResultSaver;
import org.jetbrains.java.decompiler.struct.ContextUnit;
import org.jetbrains.java.decompiler.struct.StructClass;
import org.jetbrains.java.decompiler.struct.StructContext;
import org.jetbrains.java.decompiler.struct.lazy.LazyLoader;
import org.jetbrains.java.decompiler.util.DataInputFullStream;
import java.io.File;
import java.lang.reflect.Field;
import java.util.jar.Manifest;

public class Decompiler extends IFernflowerLogger implements IDestructure, IBytecodeProvider, IResultSaver {

    private final byte[] bytes;
    private String code;

    @SneakyThrows
    public Decompiler(byte[] bytes) {
        this.bytes = bytes;

        Fernflower fernflower = new Fernflower(this, this, IFernflowerPreferences.getDefaults(), this);
        File file = new File("null.class");
        fernflower.addSource(file);

        Field structContextField = fernflower.getClass().getDeclaredField("structContext");
        structContextField.setAccessible(true);
        StructContext structContext = (StructContext) structContextField.get(fernflower);

        Field loaderField = structContext.getClass().getDeclaredField("loader");
        loaderField.setAccessible(true);
        LazyLoader loader = (LazyLoader) loaderField.get(structContext);
        loader.addClassLink(file.getName(), new LazyLoader.Link(file.getAbsolutePath(), null));

        StructClass structClass = StructClass.create(new DataInputFullStream(bytes), true, loader);
        ContextUnit contextUnit = new ContextUnit(ContextUnit.TYPE_FOLDER, null, file.getAbsolutePath(), true, this, fernflower);
        contextUnit.addClass(structClass, file.getName());

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