package net.pryoscode.decompiler.window

import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.input.TransferMode
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import net.pryoscode.decompiler.window.code.Container
import net.pryoscode.decompiler.window.sidebar.Sidebar
import java.io.File

class Window(args: Array<String>) : Stage() {

    init {
        val root = BorderPane()

        val container = Container()
        val sidebar = Sidebar(container)
        root.center = container
        root.left = sidebar

        root.setOnDragOver {
            if (it.dragboard.hasFiles() && it.dragboard.files[0].extension.equals("jar", true))
                it.acceptTransferModes(TransferMode.MOVE)
            it.consume()
        }
        root.setOnDragDropped {
            if (it.dragboard.hasFiles())
                sidebar.open(it.dragboard.files[0])
        }

        title = "Decompiler v" + javaClass.`package`.specificationVersion
        scene = Scene(root, 896.0, 560.0)
        icons.add(Image(javaClass.classLoader.getResourceAsStream("logo.png")))
        show()

        if (args.isNotEmpty())
            sidebar.open(File(args[0]))
    }

}