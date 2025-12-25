package moe.sota.decompiler.controllers

import moe.sota.decompiler.views.AboutView

class AboutController(
    private val aboutView: AboutView
) {

    fun show() {
        aboutView.isVisible = true
    }

}
