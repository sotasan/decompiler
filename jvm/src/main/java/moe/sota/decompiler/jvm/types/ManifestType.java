package moe.sota.decompiler.jvm.types;

import moe.sota.decompiler.jvm.models.FileModel;
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
