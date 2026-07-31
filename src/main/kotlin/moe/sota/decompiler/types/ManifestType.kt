package moe.sota.decompiler.types

import moe.sota.decompiler.models.FileModel
import org.fife.ui.rsyntaxtextarea.SyntaxConstants

object ManifestType : Type("icons/manifest.png", SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE) {
    override fun isFormat(fileModel: FileModel): Boolean =
        fileModel.name.endsWith(".mf", ignoreCase = true)
}
