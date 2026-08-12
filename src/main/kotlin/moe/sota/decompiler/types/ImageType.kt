package moe.sota.decompiler.types

import moe.sota.decompiler.models.FileModel

class ImageType : Type("icons/image.png", null, false) {
    override fun isFormat(fileModel: FileModel): Boolean =
        fileModel.name.lowercase().let {
            return it.endsWith(".png") ||
                it.endsWith(".jpg") ||
                it.endsWith(".jpeg") ||
                it.endsWith(".gif")
        }
}
