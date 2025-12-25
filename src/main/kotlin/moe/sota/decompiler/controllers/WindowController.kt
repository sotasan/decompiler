package moe.sota.decompiler.controllers

import moe.sota.decompiler.views.WindowView

class WindowController(
    private val windowView: WindowView
) {

    fun show() {
        windowView.isVisible = true
    }

    fun activate() {
        windowView.contentPane = windowView.splitPane
        windowView.validate()
    }

    fun dispose() {
        windowView.dispose()
    }

}
