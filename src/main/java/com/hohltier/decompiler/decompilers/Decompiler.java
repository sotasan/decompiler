package com.hohltier.decompiler.decompilers;

import lombok.Getter;

public enum Decompiler {

    Bytecode(new BytecodeDecompiler()),
    CFR(new CFRDecompiler()),
    Fernflower(new FernflowerDecompiler()),
    Procyon(new ProcyonDecompiler());

    @Getter
    private final IDecompiler instance;

    Decompiler(IDecompiler instance) {
        this.instance = instance;
    }

}