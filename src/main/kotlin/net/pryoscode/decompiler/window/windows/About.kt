package net.pryoscode.decompiler.window.windows

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import net.pryoscode.decompiler.window.Popup
import net.pryoscode.decompiler.window.components.Link

class About : Popup("About") {

    init {
        val root = VBox()
        root.alignment = Pos.CENTER
        root.children.add(Label("Decompiler"))
        root.children.add(Label("Version ${javaClass.`package`.specificationVersion ?: "0.0.0"}"))
        root.children.add(Link("https://github.com/PryosCode/Decompiler"))
        val button = Button("OK")
        button.setOnAction { dispose() }
        root.children.add(button)
        run(root)
    }

}