package moe.sota.decompiler.services

import java.io.File
import java.util.jar.JarFile
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import moe.sota.decompiler.models.ArchiveModel
import moe.sota.decompiler.models.FileModel
import moe.sota.decompiler.models.SearchEntry
import moe.sota.decompiler.models.SearchKind

class SearchServiceTest {
    private val searchService = SearchService().apply { load(archive()) }

    @Test
    fun `finds a file by a part of its name`() {
        val results = searchService.search("ain.cl")
        assertEquals(listOf("Main.class"), labels(results, SearchKind.FILE))
    }

    @Test
    fun `finds a class by a part of its name`() {
        val results = searchService.search("demo.ma")
        assertEquals(listOf("moe.sota.decompiler.demo.Main"), labels(results, SearchKind.CLASS))
    }

    @Test
    fun `finds a method by a part of its name`() {
        val results = searchService.search("ain")
        assertEquals(listOf("main"), labels(results, SearchKind.METHOD))
    }

    @Test
    fun `finds a string constant by a part of its value`() {
        val results = searchService.search("lo wor")
        assertEquals(listOf("Hello World!"), labels(results, SearchKind.STRING))
    }

    @Test
    fun `matches without regard to case`() {
        assertEquals(searchService.search("MAIN").size, searchService.search("main").size)
    }

    @Test
    fun `finds nothing for a term the archive does not contain`() {
        assertTrue(searchService.search("shirayuki").isEmpty())
    }

    @Test
    fun `finds a line in a text file that is not indexed`() {
        val results = runBlocking { searchService.searchContents("manifest-version") }
        assertEquals(listOf("Manifest-Version: 1.0"), labels(results, SearchKind.TEXT))
    }

    @Test
    fun `forgets the previous archive when a new one loads`() {
        searchService.load(ArchiveModel("empty.jar"))
        assertTrue(searchService.search("main").isEmpty())
    }

    private fun labels(results: List<SearchEntry.Result>, kind: SearchKind): List<String> =
        results.filter { it.kind == kind }.map { it.label }

    private fun archive(): ArchiveModel {
        val jar = JarFile(File(System.getProperty("demo.jar")))
        val archive = ArchiveModel("demo.jar")

        for (entry in jar.entries()) if (!entry.isDirectory)
            archive.children.add(FileModel(jar, entry))

        return archive
    }
}
