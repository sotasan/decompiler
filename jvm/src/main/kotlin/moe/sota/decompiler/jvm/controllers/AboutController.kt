package moe.sota.decompiler.jvm.controllers

import moe.sota.decompiler.jvm.views.AboutView

class AboutController(private val aboutView: AboutView) {
    fun show() {
        aboutView.isVisible = true
    }
}
