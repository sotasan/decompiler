package dev.shota.decompiler.mixins;

import dev.shota.decompiler.runtime.old.mixin.Field;
import dev.shota.decompiler.runtime.old.mixin.Mixin;
import org.jetbrains.java.decompiler.struct.StructContext;

@Mixin("org.jetbrains.java.decompiler.main.Fernflower")
public interface IMixinFernflower {

    @Field("structContext")
    StructContext getStructContext();

}