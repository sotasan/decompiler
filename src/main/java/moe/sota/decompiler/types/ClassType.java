package moe.sota.decompiler.types;

import moe.sota.decompiler.models.FileModel;
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
