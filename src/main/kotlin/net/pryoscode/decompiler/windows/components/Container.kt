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

    fun open(path: String, text: String) {
        tabs.add(Preview(text))
    }

    class Preview(text: String) : Tab() {

        init {
            val codeArea = CodeArea(text)
            codeArea.editableProperty().set(false)
            codeArea.paragraphGraphicFactory = LineNumberFactory.get(codeArea)
            content = codeArea
        }

    }

}