package dev.shota.decompiler.old.dialogs

import dev.shota.decompiler.old.menu.view.items.Language
import dev.shota.decompiler.old.utils.assets
import dev.shota.decompiler.old.utils.styles
import javafx.geometry.HPos
import javafx.geometry.VPos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.awt.Desktop
import java.io.ByteArrayInputStream
import java.net.URI
import java.util.Properties

class About : Dialog("help.about") {

    init {
        val root = GridPane().apply {
            id = "root"
            stylesheets.add(styles("about"))
        }

        ImageView(Image(ByteArrayInputStream(assets("logo/logo.png")))).run {
            GridPane.setValignment(this, VPos.TOP)
            root.add(this, 0, 0)
            isPreserveRatio = true
            fitWidth = 50.0
        }

        VBox().run {
            root.add(this, 1, 0)
            val properties = Properties()
            properties.load(javaClass.classLoader.getResourceAsStream("application.properties"))
            children.addAll(
                Label("Decompiler").apply { id = "title" },
                Label("${Language.get("about.version").value} ${properties["version"]}"),
                Label("${Language.get("about.copyright").value} 2022 shota")
            )
        }

        val controls = HBox().apply { id = "controls" }
        GridPane.setHalignment(controls, HPos.RIGHT)
        root.add(controls, 1, 1)

        Button(Language.get("about.github").value).run {
            controls.children.add(this)
            setOnAction {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
                    Desktop.getDesktop().browse(URI("https://github.com/sho7a/Decompiler"))
            }
        }

        val button = Button(Language.get("about.ok").value).apply {
            controls.children.add(this)
            isDefaultButton = true
            setOnAction {
                dispose()
            }
        }

        run(root)
        button.requestFocus()
    }

}