package moe.sota.decompiler.jvm.types

import moe.sota.decompiler.jvm.models.FileModel

class ImageType : Type("icons/image.png", null) {
    override fun isFormat(fileModel: FileModel): Boolean =
        fileModel.name.lowercase().let {
            return it.endsWith(".png") ||
                it.endsWith(".jpg") ||
                it.endsWith(".jpeg") ||
                it.endsWith(".gif")
        }
}
