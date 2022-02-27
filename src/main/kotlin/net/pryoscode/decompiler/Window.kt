package net.pryoscode.decompiler

import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

class Window : Stage() {

    init {
        val root = BorderPane()

        title = "Decompiler v" + javaClass.`package`.implementationVersion
        scene = Scene(root, 848.0, 480.0)

        show()
    }

}