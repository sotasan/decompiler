package moe.sota.decompiler.jvm.models;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public abstract class BaseModel implements Comparable<BaseModel> {
    private final List<BaseModel> children = new ArrayList<>();
    private final String path;
    public String name;

    public BaseModel(String path, boolean directory) {
        this.path = path;
        name = directory ? path.substring(0, path.length() - 1) : path;
        name = name.substring(name.lastIndexOf('/') + 1);
    }

    public List<BaseModel> getChildren() {
        return children;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(@NotNull BaseModel baseModel) {
        Class<? extends BaseModel> class1 = getClass();
        Class<? extends BaseModel> class2 = baseModel.getClass();
        return class1.equals(class2)
                ? getName().compareToIgnoreCase(baseModel.getName())
                : class1.equals(FileModel.class) ? 1 : class2.equals(FileModel.class) ? -1 : 0;
    }
}
