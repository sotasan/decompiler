package dev.shota.decompiler.runtime.old;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.Field;

@UtilityClass
public class Accessor {

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static <T> T get(@NotNull Object object, String field) {
        Field f = object.getClass().getDeclaredField(field);
        f.setAccessible(true);
        Object o = f.get(object);
        return (T) o;
    }

}
