package dev.shota.decompiler.window.sidebar

import javafx.scene.control.TreeCell
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import dev.shota.decompiler.window.container.Container

class Cell : TreeCell<Entry>() {

    init {
        addEventHandler(MouseEvent.MOUSE_CLICKED, ::mouseClicked)
        addEventHandler(MouseEvent.MOUSE_PRESSED, ::mousePressed)
    }

    override fun updateItem(item: Entry?, empty: Boolean) {
        super.updateItem(item, empty)
        if (empty || item == null) {
            text = ""
            graphic = null
        } else {
            text = item.name
            graphic = ImageView(item.type.icon)
        }
    }

    private fun mouseClicked(event: MouseEvent) {
        if (item != null && event.button == MouseButton.PRIMARY) {
            if (item.type == Type.ARCHIVE || item.type == Type.PACKAGE)
                treeItem.isExpanded = !treeItem.isExpanded
            else if (item.type != Type.FILE)
                Container.open(item)
        }
        event.consume()
    }

    private fun mousePressed(event: MouseEvent) {
        if (event.clickCount % 2 == 0 && event.button == MouseButton.PRIMARY)
            event.consume()
    }

}