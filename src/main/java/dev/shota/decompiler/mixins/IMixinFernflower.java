package dev.shota.decompiler.mixins;

import dev.shota.decompiler.reflection.mixin.Field;
import dev.shota.decompiler.reflection.mixin.Mixin;
import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.struct.StructContext;

@Mixin(Fernflower.class)
public interface IMixinFernflower {

    @Field("structContext")
    StructContext getStructContex();

}