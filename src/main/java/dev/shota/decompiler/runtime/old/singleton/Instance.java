package dev.shota.decompiler.runtime.old.singleton;

import lombok.SneakyThrows;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class Instance {

    private static final List<Object> OBJECTS = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static synchronized <T> T get(Class<T> clazz) {
        for (Object object : OBJECTS)
            if (clazz.isInstance(object))
                return (T) object;

        if (!clazz.isAnnotationPresent(Singleton.class))
            throw new SingletonException(Singleton.class.getCanonicalName());

        for (Constructor<?> constructor : clazz.getDeclaredConstructors())
            if (constructor.canAccess(null))
                throw new SingletonException(constructor.getName());

        Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        Object object = constructor.newInstance();
        OBJECTS.add(object);
        return (T) object;
    }

}