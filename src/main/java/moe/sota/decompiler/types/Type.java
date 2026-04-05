package moe.sota.decompiler.types;

import moe.sota.decompiler.models.FileModel;

public abstract class Type {
    private final String icon;
    private final String syntax;

    public Type(String icon, String syntax) {
        this.icon = icon;
        this.syntax = syntax;
    }

    public abstract boolean isFormat(FileModel fileModel);

    public String getIcon() {
        return icon;
    }

    public String getSyntax() {
        return syntax;
    }
}
