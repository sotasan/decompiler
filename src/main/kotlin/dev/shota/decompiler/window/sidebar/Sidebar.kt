package dev.shota.decompiler.window.sidebar

import com.formdev.flatlaf.util.SystemInfo
import dev.shota.decompiler.window.WindowOld
import javafx.application.Platform
import javafx.scene.control.TreeView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import java.awt.GraphicsEnvironment
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent

object Sidebar : BorderPane() {

    private val tree = TreeView<Entry>()

    init {
        minWidth = 100.0

        if (SystemInfo.isMacOS && SystemInfo.isMacFullWindowContentSupported) {
            val space = Pane()
            space.prefHeight = 25.0
            top = space

            WindowOld.addComponentListener(object : ComponentAdapter() {
                override fun componentResized(e: ComponentEvent?) {
                    Platform.runLater {
                        top = if (WindowOld.height > GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds.height) null else space
                    }
                }
            })
        }

        center = tree
        tree.setCellFactory { Cell() }
        tree.addEventHandler(KeyEvent.KEY_PRESSED) {
            if (it.code == KeyCode.ENTER) {
                val item = tree.selectionModel.selectedItem
                if (item != null) {
                    if (item.value.type == Type.ARCHIVE || item.value.type == Type.PACKAGE)
                        item.isExpanded = !item.isExpanded
                    // else if (item.value.type != Type.FILE)
                        // Container.open(item.value)
                }
            }
        }
    }

}