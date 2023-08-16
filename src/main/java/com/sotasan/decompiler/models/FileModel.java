package com.sotasan.decompiler.models;

import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileModel extends BaseModel {

    @Getter private final JarFile jarFile;
    @Getter private final JarEntry jarEntry;

    public FileModel(JarFile jarFile, @NotNull JarEntry jarEntry) {
        super(jarEntry.getName(), false);
        this.jarFile = jarFile;
        this.jarEntry = jarEntry;
        setIcon(isClass() ? "icons/class.png" : "icons/file.png");
    }

    @SneakyThrows
    public byte[] getBytes() {
        return jarFile.getInputStream(jarEntry).readAllBytes();
    }

}