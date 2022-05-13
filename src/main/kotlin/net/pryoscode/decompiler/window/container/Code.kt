package net.pryoscode.decompiler.window.container

import javafx.collections.ListChangeListener
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.Tab
import javafx.scene.image.ImageView
import net.pryoscode.decompiler.window.sidebar.Entry
import org.fxmisc.flowless.VirtualizedScrollPane
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory

class Code(val entry: Entry, code: String) : Tab() {

    init {
        text = entry.name
        graphic = ImageView(entry.type.icon)

        val codeArea = CodeArea(code)
        codeArea.isEditable = false
        codeArea.paragraphGraphicFactory = LineNumberFactory.get(codeArea)
        content = VirtualizedScrollPane(codeArea)

        val menu = ContextMenu()
        val close = MenuItem("Close")
        val closeOthers = MenuItem("Close Others")
        val closeAll = MenuItem("Close All")
        Container.tabs.addListener(ListChangeListener {
            val multiple = Container.tabs.size == 1
            closeOthers.isDisable = multiple
            closeAll.isDisable = multiple
        })
        close.setOnAction { Container.tabs.remove(this) }
        closeOthers.setOnAction {
            val tabs = Container.tabs.iterator()
            while (tabs.hasNext()) {
                if (!tabs.next().equals(this))
                    tabs.remove()
            }
        }
        closeAll.setOnAction { Container.tabs.clear() }
        menu.items.addAll(close, closeOthers, closeAll)
        contextMenu = menu

        Container.tabs.add(this)
        Container.selectionModel.select(this)
    }

}