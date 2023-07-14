package com.hohltier.decompiler.models;

import lombok.Getter;
import lombok.SneakyThrows;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class BaseModel {

    @Getter private final List<BaseModel> children = new ArrayList<>();
    @Getter private final String path;
    @Getter private final String name;
    @Getter private Image icon;

    public BaseModel(String path, boolean directory) {
        this.path = path;

        if (directory) {
            String t = path.substring(0, path.length() - 1);
            this.name = t.substring(t.lastIndexOf("/") + 1);
        } else {
            name = path;
        }
    }

    public boolean isClass() {
        return name.toLowerCase().endsWith(".class");
    }

    @SneakyThrows
    public void setIcon(String path) {
        icon = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)).readAllBytes());
    }

}