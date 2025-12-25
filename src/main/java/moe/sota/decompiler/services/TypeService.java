package moe.sota.decompiler.services;

import lombok.experimental.UtilityClass;
import moe.sota.decompiler.models.FileModel;
import moe.sota.decompiler.types.ClassType;
import moe.sota.decompiler.types.ImageType;
import moe.sota.decompiler.types.ManifestType;
import moe.sota.decompiler.types.Type;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@UtilityClass
public class TypeService {

    private static final List<Type> TYPES = List.of(
        new ClassType(),
        new ImageType(),
        new ManifestType()
    );

    public static @Nullable Type getType(FileModel fileModel) {
        for (Type format : TYPES)
            if (format.isFormat(fileModel))
                return format;
        return null;
    }

}
