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
    public int compareTo(@NotNull BaseModel baseModel) {
        Class<? extends BaseModel> class1 = getClass();
        Class<? extends BaseModel> class2 = baseModel.getClass();
        return class1.equals(class2) ? getName().compareToIgnoreCase(baseModel.getName())
                : class1.equals(FileModel.class) ? 1
                : class2.equals(FileModel.class) ? -1
                : 0;
    }

}