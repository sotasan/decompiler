package moe.sota.decompiler.services

import moe.sota.decompiler.models.FileModel
import moe.sota.decompiler.types.ClassType
import moe.sota.decompiler.types.ImageType
import moe.sota.decompiler.types.ManifestType
import moe.sota.decompiler.types.Type

object TypeService {
    private val types = listOf(ClassType, ImageType, ManifestType)

    fun getType(fileModel: FileModel): Type? = types.firstOrNull { it.isFormat(fileModel) }
}
