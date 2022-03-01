package net.pryoscode.decompiler.window.sidebar

import javafx.scene.control.TreeCell
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import net.pryoscode.decompiler.window.code.Container
import org.kordamp.ikonli.feather.Feather
import org.kordamp.ikonli.javafx.FontIcon

class Cell(private val container: Container) : TreeCell<Entry>() {

    init {
        addEventHandler(MouseEvent.MOUSE_CLICKED, ::mouseClicked)
        addEventHandler(MouseEvent.MOUSE_PRESSED, ::mousePressed)
    }

    override fun updateItem(item: Entry?, empty: Boolean) {
        super.updateItem(item, empty)
        disclosureNode = null
        if (empty || item == null) {
            text = ""
            graphic = null
        } else {
            text = item.name
            graphic = when (item.type) {
                Type.ARCHIVE -> FontIcon(Feather.ARCHIVE)
                Type.PACKAGE -> FontIcon(Feather.PACKAGE)
                Type.CLASS -> FontIcon(Feather.CODE)
                Type.FILE -> FontIcon(Feather.FILE)
            }
        }
    }

    private fun mouseClicked(event: MouseEvent) {
        if (item != null && event.button == MouseButton.PRIMARY) {
            if (item.type == Type.CLASS)
                container.open(item)
            if (item.type == Type.PACKAGE)
                treeItem.isExpanded = !treeItem.isExpanded
        }
        event.consume()
    }

    private fun mousePressed(event: MouseEvent) {
        if (event.clickCount % 2 == 0 && event.button == MouseButton.PRIMARY)
            event.consume()
    }

}