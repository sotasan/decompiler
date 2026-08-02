package moe.sota.decompiler.models

class ArchiveModel(path: String) : BaseModel(path, false) {
    init {
        loadIcon("icons/archive.png")
    }
}
