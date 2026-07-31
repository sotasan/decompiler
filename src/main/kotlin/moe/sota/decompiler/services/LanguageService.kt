package moe.sota.decompiler.services

import java.util.Locale
import java.util.ResourceBundle

class LanguageService {
    private val resourceBundle = ResourceBundle.getBundle("langs/language", Locale.getDefault())

    fun getString(key: String): String = resourceBundle.getString(key)
}
