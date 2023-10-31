package com.sotasan.decompiler.transformers;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

public enum Transformer {

    CFR(CFRTransformer.class),
    Procyon(ProcyonTransformer.class),
    Vineflower(VineflowerTransformer.class);

    private final Class<? extends ITransformer> clazz;

    Transformer(Class<? extends ITransformer> clazz) {
        this.clazz = clazz;
    }

    @SneakyThrows
    public @NotNull ITransformer newInstance() {
        return clazz.getConstructor().newInstance();
    }

}