package dev.shota.decompiler.window.container

import com.formdev.flatlaf.util.SystemInfo
import javafx.scene.control.TabPane
import dev.shota.decompiler.Decompiler
import dev.shota.decompiler.window.sidebar.Entry
import dev.shota.decompiler.window.sidebar.Type
import org.jetbrains.java.decompiler.util.InterpreterUtil

object Container : TabPane() {

    init {
        minWidth = 300.0
        tabClosingPolicy = TabClosingPolicy.ALL_TABS
        tabDragPolicy = if (SystemInfo.isMacFullWindowContentSupported) TabDragPolicy.FIXED else TabDragPolicy.REORDER
    }

    fun open(entry: Entry) {
        var tab: Code? = null
        for (t in tabs) {
            if ((t as Code).entry == entry) {
                tab = t
                break
            }
        }
        if (tab == null) {
            if (entry.type == Type.CLASS)
                Decompiler(entry)
            else
                Code(entry, String(InterpreterUtil.getBytes(entry.file, entry.entry)))
        } else
            selectionModel.select(tab)
    }

}