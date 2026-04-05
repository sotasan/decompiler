package moe.sota.decompiler.services;

import java.util.List;
import lombok.experimental.UtilityClass;
import moe.sota.decompiler.models.FileModel;
import moe.sota.decompiler.types.ClassType;
import moe.sota.decompiler.types.ImageType;
import moe.sota.decompiler.types.ManifestType;
import moe.sota.decompiler.types.Type;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class TypeService {
    private static final List<Type> TYPES = List.of(new ClassType(), new ImageType(), new ManifestType());

    public static @Nullable Type getType(FileModel fileModel) {
        for (Type format : TYPES) if (format.isFormat(fileModel)) return format;
        return null;
    }
}
