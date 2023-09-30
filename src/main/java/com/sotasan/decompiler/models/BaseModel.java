package com.sotasan.decompiler.models;

import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public abstract class BaseModel implements Comparable<BaseModel> {

    private final List<BaseModel> children = new ArrayList<>();
    private final String path;
    private String name;
    private Image icon;

    public BaseModel(String path, boolean directory) {
        this.path = path;
        name = directory ? path.substring(0, path.length() - 1) : path;
        name = name.substring(name.lastIndexOf('/') + 1);
    }

    @SneakyThrows
    public void setIcon(String path) {
        icon = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)).readAllBytes());
    }

    @Override
    public int compareTo(@NotNull BaseModel o) {
        Class<? extends BaseModel> thisClazz = this.getClass();
        Class<? extends BaseModel> otherClazz = o.getClass();
        if (thisClazz.equals(otherClazz)) {
            return this.getName().compareToIgnoreCase(o.getName());
        } else if (thisClazz.equals(FileModel.class)) {
            return +1;
        } else if (otherClazz.equals(FileModel.class)) {
            return -1;
        }
        return 0;
    }

}