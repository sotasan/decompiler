package moe.sota.decompiler.types

import moe.sota.decompiler.models.FileModel

abstract class Type(val icon: String, val syntax: String?, val text: Boolean) {
    abstract fun isFormat(fileModel: FileModel): Boolean
}
