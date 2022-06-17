package dev.shota.decompiler.window.popup

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import dev.shota.decompiler.window.utils.Link
import dev.shota.decompiler.window.utils.translate

class About : Popup(translate("help.about")) {

    init {
        val root = VBox()
        root.alignment = Pos.CENTER
        root.children.add(Label("Decompiler"))
        root.children.add(Label("Version ${javaClass.`package`.specificationVersion ?: "0.0.0"}"))
        root.children.add(Link("https://github.com/shotav/Decompiler"))
        val button = Button("OK")
        button.setOnAction { dispose() }
        root.children.add(button)
        run(root)
    }

}