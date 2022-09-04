package dev.shota.decompiler.runtime.old.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Mixin {

    String value();

}