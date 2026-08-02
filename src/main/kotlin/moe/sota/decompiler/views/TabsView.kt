package moe.sota.decompiler.views

import com.formdev.flatlaf.extras.components.FlatComboBox
import com.formdev.flatlaf.extras.components.FlatTabbedPane
import com.formdev.flatlaf.extras.components.FlatToolBar
import java.awt.Dimension
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.BorderFactory
import javax.swing.Box
import javax.swing.DefaultComboBoxModel
import moe.sota.decompiler.transformers.Transformer

class TabsView : FlatTabbedPane() {
    val comboBox: FlatComboBox<Transformer>

    init {
        isHasFullBorder = true
        isTabsClosable = true
        minimumSize = Dimension(250, 0)
        tabLayoutPolicy = SCROLL_TAB_LAYOUT
        tabType = TabType.card
        addMouseListener(TabMouseAdapter())
        setTabCloseCallback { _, tabIndex -> remove(tabIndex) }

        val toolBar =
            FlatToolBar().apply {
                border = BorderFactory.createEmptyBorder(0, 5, 0, 5)
                add(Box.createHorizontalGlue())
            }
        trailingComponent = toolBar

        comboBox =
            FlatComboBox<Transformer>().apply {
                val dimension = Dimension(150, 25)
                isFocusable = false
                maximumSize = dimension
                model = DefaultComboBoxModel(Transformer.entries.toTypedArray())
                preferredSize = dimension
                selectedItem = Transformer.Vineflower
            }
        toolBar.add(comboBox)
    }
}

private class TabMouseAdapter : MouseAdapter() {
    private var index = 0

    override fun mousePressed(event: MouseEvent) {
        val tabbedPane = event.source as FlatTabbedPane
        if (event.button == MouseEvent.BUTTON2) index = tabbedPane.indexAtLocation(event.x, event.y)
    }

    override fun mouseReleased(event: MouseEvent) {
        if (event.button == MouseEvent.BUTTON2 && index != -1)
            (event.source as FlatTabbedPane).remove(index)
    }
}
