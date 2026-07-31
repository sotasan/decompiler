package moe.sota.decompiler.models

class PackageModel(path: String) : BaseModel(path, true) {
    init {
        loadIcon("icons/package.png")
    }
}
