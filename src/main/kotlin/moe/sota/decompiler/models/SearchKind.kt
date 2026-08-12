package moe.sota.decompiler.models

enum class SearchKind(val key: String) {
    FILE("search.files"),
    CLASS("search.classes"),
    METHOD("search.methods"),
    FIELD("search.fields"),
    STRING("search.strings"),
    TEXT("search.text"),
}
