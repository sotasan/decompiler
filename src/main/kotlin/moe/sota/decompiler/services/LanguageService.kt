package moe.sota.decompiler.services

import java.util.*

class LanguageService {

    private val resourceBundle = ResourceBundle.getBundle("langs/language", Locale.getDefault())

    fun getString(key: String): String {
        return resourceBundle.getString(key)
    }

}
