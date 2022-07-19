package dev.shota.decompiler.window.dialogs

import dev.shota.decompiler.window.menu.view.items.Language
import dev.shota.decompiler.window.utils.styles
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
import java.net.URI

class About : Dialog("help.about") {

    init {
        val root = GridPane().apply {
            id = "root"
            stylesheets.add(styles("about.styl"))
        }

        ImageView(Image(javaClass.classLoader.getResourceAsStream("logo/logo.png"))).run {
            GridPane.setValignment(this, VPos.TOP)
            root.add(this, 0, 0)
            isPreserveRatio = true
            fitWidth = 50.0
        }

        VBox().run {
            root.add(this, 1, 0)
            children.addAll(
                Label("Decompiler").apply { id = "title" },
                Label("${Language.get("about.version").value} ${javaClass.`package`.specificationVersion ?: "0.0.0"}"),
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