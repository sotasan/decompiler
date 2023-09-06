package com.sotasan.decompiler.transformers;

import lombok.Getter;

@Getter
public enum Transformer {

    CFR(new CFRTransformer()),
    Procyon(new ProcyonTransformer()),
    Vineflower(new VineflowerTransformer());

    private final ITransformer instance;

    Transformer(ITransformer instance) {
        this.instance = instance;
    }

}