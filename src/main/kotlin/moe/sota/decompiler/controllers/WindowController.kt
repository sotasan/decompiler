package moe.sota.decompiler.controllers

import moe.sota.decompiler.views.WindowView

class WindowController : BaseController<WindowView>(WindowView()) {

    fun show() {
        view.isVisible = true
    }

    fun activate() {
        view.contentPane = view.splitPane
        view.validate()
    }

    fun dispose() {
        view.dispose()
    }

}
