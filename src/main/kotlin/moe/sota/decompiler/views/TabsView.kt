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

    val comboBox: FlatComboBox<Transformer?>
    val toolBar: FlatToolBar

    init {
        addMouseListener(TabMouseAdapter())
        isHasFullBorder = true
        minimumSize = Dimension(250, 0)
        isTabsClosable = true
        setTabCloseCallback(::onTabClose)
        setTabLayoutPolicy(SCROLL_TAB_LAYOUT)
        tabType = TabType.card

        val dimension = Dimension(150, 25)
        comboBox = FlatComboBox<Transformer?>()
        comboBox.setFocusable(false)
        comboBox.maximumSize = dimension
        comboBox.setModel(DefaultComboBoxModel<Transformer?>(Transformer.entries.toTypedArray()))
        comboBox.preferredSize = dimension
        comboBox.setSelectedItem(Transformer.Vineflower)

        toolBar = FlatToolBar()
        toolBar.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5))
        toolBar.add(Box.createHorizontalGlue())
        toolBar.add(comboBox)
        trailingComponent = toolBar
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
