package moe.sota.decompiler.jvm.models;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

public class FileModel extends BaseModel {
    private final JarFile jarFile;
    private final JarEntry jarEntry;

    public FileModel(JarFile jarFile, @NotNull JarEntry jarEntry) {
        super(jarEntry.getName(), false);
        this.jarFile = jarFile;
        this.jarEntry = jarEntry;
    }

    @SneakyThrows
    public byte[] getBytes() {
        return jarFile.getInputStream(jarEntry).readAllBytes();
    }
}
