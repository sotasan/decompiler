package net.pryoscode.decompiler.window.sidebar

import javafx.scene.control.TreeCell
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import net.pryoscode.decompiler.window.code.Container

class Cell(private val container: Container) : TreeCell<Entry>() {

    private val ARCHIVE = Image(javaClass.classLoader.getResourceAsStream("archive.png"))
    private val PACKAGE = Image(javaClass.classLoader.getResourceAsStream("package.png"))
    private val CLASS = Image(javaClass.classLoader.getResourceAsStream("class.png"))
    private val FILE = Image(javaClass.classLoader.getResourceAsStream("file.png"))

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
            graphic = when (item.type) {
                Type.ARCHIVE -> ImageView(ARCHIVE)
                Type.PACKAGE -> ImageView(PACKAGE)
                Type.CLASS -> ImageView(CLASS)
                Type.FILE -> ImageView(FILE)
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