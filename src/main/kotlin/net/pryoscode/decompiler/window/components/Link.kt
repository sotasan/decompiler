package net.pryoscode.decompiler.window.components

import javafx.scene.control.Hyperlink
import java.awt.Desktop
import java.net.URI
import javax.swing.SwingUtilities

class Link(url: String) : Hyperlink(url) {

    init {
        setOnAction {
            SwingUtilities.invokeLater {
                Desktop.getDesktop().browse(URI(text))
            }
        }
    }

}