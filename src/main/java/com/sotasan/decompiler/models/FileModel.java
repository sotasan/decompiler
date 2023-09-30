package com.sotasan.decompiler.models;

import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Getter
public class FileModel extends BaseModel {

    private final JarFile jarFile;
    private final JarEntry jarEntry;

    public FileModel(JarFile jarFile, @NotNull JarEntry jarEntry) {
        super(jarEntry.getName(), false);
        this.jarFile = jarFile;
        this.jarEntry = jarEntry;
        setIcon(isClass() ? "icons/class.png" : "icons/file.png");
    }

    public boolean isClass() {
        return this.getName().toLowerCase(Locale.ROOT).endsWith(".class");
    }

    @SneakyThrows
    public byte[] getBytes() {
        return jarFile.getInputStream(jarEntry).readAllBytes();
    }

}