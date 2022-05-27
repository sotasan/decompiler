package net.pryoscode.decompiler.window.sidebar

import javafx.scene.Cursor
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import net.pryoscode.decompiler.window.Window
import net.pryoscode.decompiler.window.container.Container

class Bar : Pane() {

    init {
        prefWidth = 5.0
        prefHeight = Double.MAX_VALUE
        cursor = Cursor.H_RESIZE
        addEventHandler(MouseEvent.MOUSE_DRAGGED, ::mouseDragged)
    }

    private fun mouseDragged(event: MouseEvent) {
        if (event.sceneX < Window.width - Container.minWidth)
            Sidebar.prefWidth = event.sceneX
    }

}