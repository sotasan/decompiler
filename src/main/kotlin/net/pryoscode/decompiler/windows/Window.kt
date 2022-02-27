package net.pryoscode.decompiler.windows

import javafx.scene.Scene
import javafx.scene.input.TransferMode
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import net.pryoscode.decompiler.windows.components.Container
import net.pryoscode.decompiler.windows.components.Sidebar

class Window : Stage() {

    init {
        val root = BorderPane()

        val container = Container()
        val sidebar = Sidebar(container)
        root.center = container
        root.left = sidebar

        root.setOnDragOver {
            if (it.dragboard.hasFiles() && it.dragboard.files[0].extension.equals("jar", ignoreCase = true))
                it.acceptTransferModes(TransferMode.LINK)
            it.consume()
        }
        root.setOnDragDropped {
            if (it.dragboard.hasFiles())
                sidebar.open(it.dragboard.files[0])
        }

        title = "Decompiler v" + javaClass.`package`.implementationVersion
        scene = Scene(root, 848.0, 480.0)
    }

}