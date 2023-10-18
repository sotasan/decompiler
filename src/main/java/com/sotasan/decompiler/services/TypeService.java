package com.sotasan.decompiler.services;

import com.sotasan.decompiler.models.FileModel;
import com.sotasan.decompiler.types.ClassType;
import com.sotasan.decompiler.types.ManifestType;
import com.sotasan.decompiler.types.Type;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;
import java.util.List;

@UtilityClass
public class TypeService {

    private static final List<Type> TYPES = List.of(
            new ClassType(),
            new ManifestType()
    );

    public static @Nullable Type getType(FileModel fileModel) {
        for (Type format : TYPES)
            if (format.isFormat(fileModel))
                return format;
        return null;
    }

}