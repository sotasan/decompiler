package net.pryoscode.decompiler.windows.components

import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory

class Container : TabPane() {

    init {
        tabDragPolicy = TabDragPolicy.REORDER
        tabClosingPolicy = TabClosingPolicy.ALL_TABS
    }

    fun open(entry: Sidebar.Entry) {
        val tab = Preview(entry)
        tabs.add(tab)
        selectionModel.select(tab)
    }

    class Preview(entry: Sidebar.Entry) : Tab() {

        init {
            text = entry.name
            val codeArea = CodeArea(entry.path)
            codeArea.isEditable = false
            codeArea.paragraphGraphicFactory = LineNumberFactory.get(codeArea)
            content = codeArea
        }

    }

}