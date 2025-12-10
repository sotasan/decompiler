package moe.sota.decompiler.services

import org.ktorm.database.Database
import java.util.jar.JarFile

class SearchService {

    private val database = Database.connect("jdbc:h2:mem:")

    init {
    }

    fun load(jar: JarFile) {
    }

    fun dispose() {
    }

}
