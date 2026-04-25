package moe.sota.decompiler.jvm.models;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import lombok.SneakyThrows;
import moe.sota.decompiler.jvm.services.TypeService;
import moe.sota.decompiler.jvm.types.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FileModel extends BaseModel {
    private final JarFile jarFile;
    private final JarEntry jarEntry;

    @Nullable
    private final Type type;

    public FileModel(JarFile jarFile, @NotNull JarEntry jarEntry) {
        super(jarEntry.getName(), false);
        this.jarFile = jarFile;
        this.jarEntry = jarEntry;
        type = TypeService.getType(this);
        setIcon(type != null ? type.getIcon() : "icons/file.png");
    }

    @SneakyThrows
    public byte[] getBytes() {
        return jarFile.getInputStream(jarEntry).readAllBytes();
    }

    public Type getType() {
        return type;
    }
}
