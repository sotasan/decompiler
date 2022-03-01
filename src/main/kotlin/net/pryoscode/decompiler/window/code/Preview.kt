package net.pryoscode.decompiler.window.code

import javafx.scene.control.Tab
import net.pryoscode.decompiler.window.sidebar.Entry
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory

class Preview(entry: Entry) : Tab() {

    init {
        text = entry.name
        val codeArea = CodeArea(entry.path)
        codeArea.isEditable = false
        codeArea.paragraphGraphicFactory = LineNumberFactory.get(codeArea)
        content = codeArea
    }

}