package com.hohltier.decompiler.models;

import lombok.Getter;
import javax.swing.tree.DefaultMutableTreeNode;

public abstract class BaseModel extends DefaultMutableTreeNode {

    @Getter private final String name;

    public BaseModel(String name) {
        super(name);
        this.name = name;
    }

}