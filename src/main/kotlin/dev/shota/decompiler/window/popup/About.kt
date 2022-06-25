package dev.shota.decompiler.window.popup

import dev.shota.decompiler.window.menu.view.items.Language
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import dev.shota.decompiler.window.utils.Link

class About : Popup("help.about") {

    init {
        val root = VBox()
        root.alignment = Pos.CENTER
        root.children.add(Label("Decompiler"))
        root.children.add(Label("${Language.get("about.version").value} ${javaClass.`package`.specificationVersion ?: "0.0.0"}"))
        root.children.add(Link("https://github.com/shotav/Decompiler"))
        val button = Button(Language.get("about.ok").value)
        button.setOnAction { dispose() }
        root.children.add(button)
        run(root)
    }

}