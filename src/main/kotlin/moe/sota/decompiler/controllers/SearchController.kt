package moe.sota.decompiler.controllers

import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.WindowEvent
import java.awt.event.WindowFocusListener
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.sota.decompiler.models.SearchEntry
import moe.sota.decompiler.models.SearchKind
import moe.sota.decompiler.services.LanguageService
import moe.sota.decompiler.services.SearchService
import moe.sota.decompiler.views.SearchView

private const val DEBOUNCE = 150L

class SearchController(
    private val languageService: LanguageService,
    private val searchService: SearchService,
    private val searchView: SearchView,
    private val tabsController: TabsController,
) : DocumentListener, KeyListener, WindowFocusListener {
    private val scope = MainScope()
    private var job: Job? = null

    init {
        searchView.addWindowFocusListener(this)
        searchView.list.addMouseListener(SearchMouseAdapter(this))
        searchView.textField.addKeyListener(this)
        searchView.textField.document.addDocumentListener(this)
    }

    fun show() {
        val owner = searchView.owner
        searchView.setSize(maxOf(owner.width * 2 / 5, 400), owner.height / 2)
        searchView.setLocationRelativeTo(owner)
        searchView.textField.text = ""
        searchView.isVisible = true
        searchView.textField.requestFocusInWindow()
    }

    fun open() {
        val entry = searchView.list.selectedValue
        if (entry !is SearchEntry.Result) return

        searchView.isVisible = false
        tabsController.addTab(entry.fileModel)
    }

    override fun changedUpdate(event: DocumentEvent?) = search()

    override fun insertUpdate(event: DocumentEvent?) = search()

    override fun removeUpdate(event: DocumentEvent?) = search()

    override fun keyPressed(event: KeyEvent) {
        when (event.keyCode) {
            KeyEvent.VK_ESCAPE -> searchView.isVisible = false
            KeyEvent.VK_ENTER -> open()
            KeyEvent.VK_DOWN -> move(1)
            KeyEvent.VK_UP -> move(-1)
            else -> return
        }

        event.consume()
    }

    override fun keyReleased(event: KeyEvent?) {}

    override fun keyTyped(event: KeyEvent?) {}

    override fun windowGainedFocus(event: WindowEvent?) {}

    override fun windowLostFocus(event: WindowEvent?) {
        searchView.isVisible = false
    }

    private fun search() {
        val query = searchView.textField.text

        job?.cancel()
        job = scope.launch {
            delay(DEBOUNCE)
            val results = withContext(Dispatchers.IO) { searchService.search(query) }
            setEntries(query, results, query.isNotBlank())
            if (query.isBlank()) return@launch

            val contents = withContext(Dispatchers.IO) { searchService.searchContents(query) }
            setEntries(query, results + contents, false)
        }
    }

    private fun setEntries(query: String, results: List<SearchEntry.Result>, searching: Boolean) {
        val listModel = searchView.listModel
        listModel.clear()

        for (kind in SearchKind.entries) {
            val group = results.filter { it.kind == kind }
            if (group.isEmpty()) continue

            listModel.addElement(SearchEntry.Header(languageService.getString(kind.key)))
            for (result in group) listModel.addElement(result)
        }

        if (searching)
            listModel.addElement(SearchEntry.Header(languageService.getString("search.searching")))
        else if (listModel.isEmpty && query.isNotBlank())
            listModel.addElement(SearchEntry.Header(languageService.getString("search.empty")))

        select(0, 1)
    }

    private fun move(step: Int) = select(searchView.list.selectedIndex + step, step)

    private fun select(from: Int, step: Int) {
        val listModel = searchView.listModel
        var index = from

        while (index in 0..<listModel.size) {
            if (listModel.get(index) is SearchEntry.Result) {
                searchView.list.selectedIndex = index
                searchView.list.ensureIndexIsVisible(index)
                return
            }

            index += step
        }
    }
}

private class SearchMouseAdapter(private val searchController: SearchController) : MouseAdapter() {
    override fun mousePressed(event: MouseEvent) {
        if (event.clickCount % 2 == 0) searchController.open()
    }
}
