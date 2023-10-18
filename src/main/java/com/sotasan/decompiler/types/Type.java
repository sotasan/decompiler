package com.sotasan.decompiler.types;

import com.sotasan.decompiler.models.FileModel;
import lombok.Getter;

@Getter
public abstract class Type {

    private final String icon;
    private final String syntax;

    public Type(String icon, String syntax) {
        this.icon = icon;
        this.syntax = syntax;
    }

    public abstract boolean isFormat(FileModel fileModel);

}