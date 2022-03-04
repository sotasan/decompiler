package net.pryoscode.decompiler.window.code

import javafx.scene.control.Tab
import net.pryoscode.decompiler.window.sidebar.Entry
import org.fxmisc.flowless.VirtualizedScrollPane
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory

class Preview(entry: Entry, code: String) : Tab() {

    init {
        text = entry.name
        val codeArea = CodeArea(code)
        codeArea.isEditable = false
        codeArea.paragraphGraphicFactory = LineNumberFactory.get(codeArea)
        content = VirtualizedScrollPane(codeArea)
    }

}