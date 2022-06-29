package dev.shota.decompiler.window.utils

import javafx.scene.control.Hyperlink
import java.awt.Desktop
import java.net.URI
import javax.swing.SwingUtilities

class Link(url: String) : Hyperlink(url) {

    init {
        setOnAction {
            SwingUtilities.invokeLater {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
                    Desktop.getDesktop().browse(URI(text))
            }
        }
    }

}