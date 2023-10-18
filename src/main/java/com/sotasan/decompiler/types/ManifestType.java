package com.sotasan.decompiler.types;

import com.sotasan.decompiler.models.FileModel;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.jetbrains.annotations.NotNull;

public class ManifestType extends Type {

    public ManifestType() {
        super("icons/manifest.png", SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE);
    }

    @Override
    public boolean isFormat(@NotNull FileModel fileModel) {
        return fileModel.getName().toLowerCase().endsWith(".mf");
    }

}