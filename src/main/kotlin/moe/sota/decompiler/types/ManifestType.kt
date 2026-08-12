package moe.sota.decompiler.types

import moe.sota.decompiler.models.FileModel
import org.fife.ui.rsyntaxtextarea.SyntaxConstants

class ManifestType :
    Type("icons/manifest.png", SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE, true) {
    override fun isFormat(fileModel: FileModel): Boolean =
        fileModel.name.lowercase().endsWith(".mf")
}
