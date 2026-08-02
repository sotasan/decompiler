package moe.sota.decompiler.views

import com.formdev.flatlaf.extras.components.FlatScrollPane
import com.formdev.flatlaf.extras.components.FlatTree
import java.awt.BorderLayout
import java.awt.Component
import java.awt.event.InputEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.BorderFactory
import javax.swing.ImageIcon
import javax.swing.JPanel
import javax.swing.JTree
import javax.swing.ToolTipManager
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeCellRenderer
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreeSelectionModel
import moe.sota.decompiler.controllers.TabsController
import moe.sota.decompiler.models.BaseModel
import moe.sota.decompiler.models.FileModel

class TreeView(private val tabsController: TabsController) : JPanel(BorderLayout()) {
    // TODO: only one root node
    val tree =
        FlatTree().apply {
            addKeyListener(TreeKeyListener(this@TreeView))
            addMouseListener(TreeMouseAdapter(this@TreeView))
            selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION
            cellRenderer = TreeCellRenderer()
            model = DefaultTreeModel(DefaultMutableTreeNode())
            isRootVisible = false
            showsRootHandles = true
        }

    init {
        ToolTipManager.sharedInstance().registerComponent(tree)

        val scrollPane =
            FlatScrollPane().apply {
                border = BorderFactory.createEmptyBorder()
                setViewportView(tree)
            }
        add(scrollPane)
    }

    fun addTab(event: InputEvent) {
        val path = (event.source as JTree).selectionPath ?: return
        val model = (path.lastPathComponent as DefaultMutableTreeNode).userObject
        if (model is FileModel) tabsController.addTab(model)
    }
}

private class TreeCellRenderer : DefaultTreeCellRenderer() {
    override fun getTreeCellRendererComponent(
        tree: JTree?,
        value: Any?,
        selected: Boolean,
        expanded: Boolean,
        leaf: Boolean,
        row: Int,
        focused: Boolean,
    ): Component? {
        val component =
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, focused)
        val model = (value as DefaultMutableTreeNode).userObject
        if (model is BaseModel) {
            text = model.name
            icon = ImageIcon(model.icon)
            toolTipText = model.name
        }
        return component
    }
}

private class TreeMouseAdapter(private val treeView: TreeView) : MouseAdapter() {
    override fun mousePressed(event: MouseEvent) {
        if (event.clickCount % 2 == 0) treeView.addTab(event)
    }
}

private class TreeKeyListener(private val treeView: TreeView) : KeyAdapter() {
    override fun keyPressed(keyEvent: KeyEvent) {
        if (keyEvent.extendedKeyCode == KeyEvent.VK_ENTER) treeView.addTab(keyEvent)
    }
}
