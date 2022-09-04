package dev.shota.decompiler.reflection.mixin;

import dev.shota.decompiler.mixins.IMixinFernflower;

public class MixinBootstrap {

    public static void init() {
        Class<?> clazz = IMixinFernflower.class.getAnnotation(Mixin.class).value();
        System.out.println(clazz);
    }

}