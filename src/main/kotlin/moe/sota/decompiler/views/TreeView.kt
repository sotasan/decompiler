package moe.sota.decompiler.views

import com.formdev.flatlaf.extras.components.FlatScrollPane
import com.formdev.flatlaf.extras.components.FlatTree
import moe.sota.decompiler.controllers.TabsController
import moe.sota.decompiler.models.BaseModel
import moe.sota.decompiler.models.FileModel
import java.awt.BorderLayout
import java.awt.Component
import java.awt.event.*
import javax.swing.*
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeCellRenderer
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreeSelectionModel

class TreeView(
    private val tabsController: TabsController
) : JPanel(BorderLayout()) {

    val tree: FlatTree
    val scrollPane: FlatScrollPane

    init {
        // TODO: only one root node
        tree = FlatTree()
        tree.addKeyListener(TreeKeyListener(this))
        tree.addMouseListener(TreeMouseAdapter(this))
        tree.getSelectionModel().selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION
        tree.setCellRenderer(TreeCellRenderer())
        tree.setModel(DefaultTreeModel(DefaultMutableTreeNode()))
        tree.setRootVisible(false)
        tree.setShowsRootHandles(true)
        ToolTipManager.sharedInstance().registerComponent(tree)

        scrollPane = FlatScrollPane()
        scrollPane.setBorder(BorderFactory.createEmptyBorder())
        scrollPane.setViewportView(tree)
        add(scrollPane)
    }

    fun addTab(event: InputEvent) {
        val path = (event.getSource() as JTree).selectionPath
        if (path == null)
            return

        val node = path.lastPathComponent as DefaultMutableTreeNode
        if (node.getUserObject() == null)
            return

        val model = node.getUserObject() as BaseModel?
        if (model is FileModel)
            tabsController.addTab(model)
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
        focused: Boolean
    ): Component? {
        val component = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, focused)
        val node = value as DefaultMutableTreeNode
        if (node.getUserObject() is BaseModel) {
            val model = node.getUserObject() as BaseModel
            setText(model.getName())
            setIcon(ImageIcon(model.icon))
            setToolTipText(model.name)
        }
        return component
    }

}

private class TreeMouseAdapter(
    private val treeView: TreeView
) : MouseAdapter() {

    override fun mousePressed(event: MouseEvent) {
        if (event.getClickCount() % 2 == 0)
            treeView.addTab(event)
    }

}

private class TreeKeyListener(
    private val treeView: TreeView
) : KeyListener {

    override fun keyPressed(keyEvent: KeyEvent) {
        if (keyEvent.extendedKeyCode == KeyEvent.VK_ENTER)
            treeView.addTab(keyEvent)
    }

    override fun keyReleased(keyEvent: KeyEvent?) {}
    override fun keyTyped(keyEvent: KeyEvent?) {}

}
