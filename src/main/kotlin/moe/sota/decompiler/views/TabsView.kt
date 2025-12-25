package moe.sota.decompiler.views

import com.formdev.flatlaf.extras.components.FlatComboBox
import com.formdev.flatlaf.extras.components.FlatTabbedPane
import com.formdev.flatlaf.extras.components.FlatToolBar
import moe.sota.decompiler.transformers.Transformer
import java.awt.Dimension
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.BorderFactory
import javax.swing.Box
import javax.swing.DefaultComboBoxModel
import javax.swing.JTabbedPane

class TabsView : FlatTabbedPane() {

    val comboBox: FlatComboBox<Transformer>
    val toolBar: FlatToolBar

    init {
        isHasFullBorder = true
        isTabsClosable = true
        minimumSize = Dimension(250, 0)
        tabLayoutPolicy = SCROLL_TAB_LAYOUT
        tabType = TabType.card
        addMouseListener(TabMouseAdapter())
        setTabCloseCallback(::onTabClose)

        toolBar = FlatToolBar().apply {
            border = BorderFactory.createEmptyBorder(0, 5, 0, 5)
            add(Box.createHorizontalGlue())
        }
        trailingComponent = toolBar

        comboBox = FlatComboBox<Transformer>().apply {
            val dimension = Dimension(150, 25)
            isFocusable = false
            maximumSize = dimension
            model = DefaultComboBoxModel<Transformer>(Transformer.entries.toTypedArray())
            preferredSize = dimension
            selectedItem = Transformer.Vineflower
        }
        toolBar.add(comboBox)
    }

    private fun onTabClose(tabPane: JTabbedPane?, tabIndex: Int) {
        remove(tabIndex)
    }

}

private class TabMouseAdapter : MouseAdapter() {

    private var index = 0

    override fun mousePressed(event: MouseEvent) {
        val tabbedPane = event.getSource() as FlatTabbedPane
        if (event.getButton() == MouseEvent.BUTTON2) index = tabbedPane.indexAtLocation(event.getX(), event.getY())
    }

    override fun mouseReleased(event: MouseEvent) {
        val tabbedPane = event.getSource() as FlatTabbedPane
        if (event.getButton() == MouseEvent.BUTTON2 && index != -1) tabbedPane.remove(index)
    }

}
