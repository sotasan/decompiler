package moe.sota.decompiler.types

import moe.sota.decompiler.models.FileModel

object ImageType : Type("icons/image.png", null) {
    private val extensions = listOf(".png", ".jpg", ".jpeg", ".gif")

    override fun isFormat(fileModel: FileModel): Boolean = extensions.any {
        fileModel.name.endsWith(it, ignoreCase = true)
    }
}
