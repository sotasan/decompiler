package moe.sota.decompiler.types

import moe.sota.decompiler.models.FileModel

abstract class Type(val icon: String, val syntax: String?) {
    abstract fun isFormat(fileModel: FileModel): Boolean
}
