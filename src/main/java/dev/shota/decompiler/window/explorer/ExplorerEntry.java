package dev.shota.decompiler.window.explorer;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ExplorerEntry {

    @Getter private final String name;

    public ExplorerEntry(@NotNull JarFile jarFile) {
        File file = new File(jarFile.getName());
        this.name = file.getName();
    }

    public ExplorerEntry(@NotNull JarEntry jarEntry) {
        name = jarEntry.getName();
    }

}