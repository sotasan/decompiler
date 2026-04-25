package moe.sota.decompiler.jvm.services

import java.util.*

class LanguageService {
    private val resourceBundle = ResourceBundle.getBundle("langs/language", Locale.getDefault())

    fun getString(key: String): String {
        return resourceBundle.getString(key)
    }
}
