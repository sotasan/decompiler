package moe.sota.decompiler.views

import com.formdev.flatlaf.extras.components.FlatScrollPane
import com.formdev.flatlaf.extras.components.FlatTextField
import java.awt.BorderLayout
import java.awt.Component
import javax.swing.BorderFactory
import javax.swing.DefaultListModel
import javax.swing.ImageIcon
import javax.swing.JDialog
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.ListCellRenderer
import javax.swing.ListSelectionModel
import javax.swing.UIManager
import javax.swing.border.EmptyBorder
import moe.sota.decompiler.models.SearchEntry
import moe.sota.decompiler.services.LanguageService

class SearchView(languageService: LanguageService, windowView: WindowView) : JDialog(windowView) {
    val listModel = DefaultListModel<SearchEntry>()
    val list: JList<SearchEntry>
    val scrollPane: FlatScrollPane
    val textField: FlatTextField

    init {
        defaultCloseOperation = HIDE_ON_CLOSE
        isUndecorated = true

        val root =
            JPanel(BorderLayout()).apply {
                border = BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor"))
            }
        contentPane = root

        textField =
            FlatTextField().apply {
                border = EmptyBorder(8, 8, 8, 8)
                placeholderText = languageService.getString("search.placeholder")
            }
        root.add(textField, BorderLayout.NORTH)

        list =
            JList(listModel).apply {
                cellRenderer = SearchCellRenderer()
                selectionMode = ListSelectionModel.SINGLE_SELECTION
            }

        scrollPane =
            FlatScrollPane().apply {
                border = BorderFactory.createEmptyBorder()
                setViewportView(list)
            }
        root.add(scrollPane, BorderLayout.CENTER)
    }
}

private class SearchCellRenderer : JPanel(BorderLayout(8, 0)), ListCellRenderer<SearchEntry> {
    private val label = JLabel()
    private val detail = JLabel()

    init {
        add(label, BorderLayout.WEST)
        add(detail, BorderLayout.CENTER)
    }

    override fun getListCellRendererComponent(
        list: JList<out SearchEntry>,
        value: SearchEntry,
        index: Int,
        selected: Boolean,
        focused: Boolean,
    ): Component {
        val disabled = UIManager.getColor("Label.disabledForeground")

        when (value) {
            is SearchEntry.Header -> {
                border = EmptyBorder(8, 8, 2, 8)
                label.icon = null
                label.text = value.text
                label.foreground = disabled
                detail.text = null
            }
            is SearchEntry.Result -> {
                border = EmptyBorder(2, 8, 2, 8)
                label.icon = ImageIcon(value.fileModel.icon)
                label.text = value.label
                label.foreground = if (selected) list.selectionForeground else list.foreground
                detail.text = value.detail
            }
        }

        detail.foreground = if (selected) list.selectionForeground else disabled
        background = if (selected) list.selectionBackground else list.background
        isOpaque = selected

        return this
    }
}
