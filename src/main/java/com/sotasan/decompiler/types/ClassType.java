package com.sotasan.decompiler.types;

import com.sotasan.decompiler.models.FileModel;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.jetbrains.annotations.NotNull;

public class ClassType extends Type {

    public ClassType() {
        super("icons/class.png", SyntaxConstants.SYNTAX_STYLE_JAVA);
    }

    @Override
    public boolean isFormat(@NotNull FileModel fileModel) {
        return fileModel.getName().toLowerCase().endsWith(".class");
    }

}