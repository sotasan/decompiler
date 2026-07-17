package moe.sota.decompiler.transformers

import moe.sota.decompiler.models.FileModel

interface ITransformer {
    fun transform(fileModel: FileModel): String
}
