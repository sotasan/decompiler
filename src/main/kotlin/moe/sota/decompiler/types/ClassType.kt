package moe.sota.decompiler.types

import moe.sota.decompiler.models.FileModel
import org.fife.ui.rsyntaxtextarea.SyntaxConstants

class ClassType : Type("icons/class.png", SyntaxConstants.SYNTAX_STYLE_JAVA) {
    override fun isFormat(fileModel: FileModel): Boolean =
        fileModel.name.lowercase().endsWith(".class")
}
