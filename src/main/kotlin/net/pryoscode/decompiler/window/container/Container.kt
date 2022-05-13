package net.pryoscode.decompiler.window.container

import javafx.scene.control.TabPane
import net.pryoscode.decompiler.Decompiler
import net.pryoscode.decompiler.window.sidebar.Entry
import net.pryoscode.decompiler.window.sidebar.Type
import org.jetbrains.java.decompiler.util.InterpreterUtil

object Container : TabPane() {

    init {
        tabDragPolicy = TabDragPolicy.REORDER
        tabClosingPolicy = TabClosingPolicy.ALL_TABS
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