package moe.sota.decompiler.models

sealed class SearchEntry {
    class Header(val key: String) : SearchEntry()

    class Result(
        val kind: SearchKind,
        val label: String,
        val detail: String,
        val fileModel: FileModel,
    ) : SearchEntry()
}
