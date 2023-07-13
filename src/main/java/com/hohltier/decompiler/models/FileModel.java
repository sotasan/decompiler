package com.hohltier.decompiler.models;

import lombok.Getter;
import lombok.Setter;

public class FileModel extends BaseModel {

    @Getter @Setter private byte[] bytes;

    public FileModel(String name) {
        super(name);
    }

}