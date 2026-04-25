package moe.sota.decompiler.services

import java.util.jar.JarFile
import org.ktorm.database.Database

class SearchService {
    private val database = Database.connect("jdbc:h2:mem:")

    init {}

    fun load(jar: JarFile) {}

    fun dispose() {}
}
