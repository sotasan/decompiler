package net.pryoscode.decompiler.window

import com.sun.javafx.tk.Toolkit
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.input.TransferMode
import javafx.scene.layout.BorderPane
import javafx.scene.text.Font
import javafx.stage.Stage
import net.pryoscode.decompiler.window.code.Container
import net.pryoscode.decompiler.window.sidebar.Sidebar
import java.io.File
import java.util.Base64

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

        Font.loadFont(javaClass.classLoader.getResourceAsStream("fonts/FiraSans-Regular.ttf"), Toolkit.getToolkit().fontLoader.systemFontSize.toDouble())
        Font.loadFont(javaClass.classLoader.getResourceAsStream("fonts/FiraMono-Regular.ttf"), Toolkit.getToolkit().fontLoader.systemFontSize.toDouble())
        root.stylesheets.add("data:text/css;base64," + Base64.getEncoder().encodeToString((
                "* {" +
                    "-fx-font-family: 'Fira Sans';" +
                "}" +
                ".code-area * {" +
                    "-fx-font-family: 'Fira Mono';" +
                "}"
        ).toByteArray()))

        title = "Decompiler v" + javaClass.`package`.specificationVersion
        scene = Scene(root, 896.0, 560.0)
        icons.add(Image(javaClass.classLoader.getResourceAsStream("icons/logo.png")))
        show()

        if (args.isNotEmpty())
            sidebar.open(File(args[0]))
    }

}