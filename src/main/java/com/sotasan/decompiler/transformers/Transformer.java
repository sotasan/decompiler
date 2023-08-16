package com.sotasan.decompiler.transformers;

import lombok.Getter;

public enum Transformer {

    CFR(new CFRTransformer()),
    Fernflower(new FernflowerTransformer()),
    Procyon(new ProcyonTransformer());

    @Getter
    private final ITransformer instance;

    Transformer(ITransformer instance) {
        this.instance = instance;
    }

}