package net.pryoscode.decompiler.window

import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid
import org.kordamp.ikonli.javafx.FontIcon

class Empty : BorderPane() {

    init {
        val container = VBox()
        val icon = FontIcon(FontAwesomeSolid.ARCHIVE)
        val text = Label("No file opened")
        container.children.addAll(icon, text)
        center = container
    }

}