package moe.sota.decompiler.jvm.controllers

import java.util.*
import javax.swing.SwingUtilities
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreePath
import moe.sota.decompiler.jvm.models.ArchiveModel
import moe.sota.decompiler.jvm.models.BaseModel
import moe.sota.decompiler.jvm.views.TreeView

class TreeController(private val treeView: TreeView) {
    fun setArchive(archiveModel: ArchiveModel) {
        val treeModel = treeView.tree.model as DefaultTreeModel
        val rootNode = treeModel.getRoot() as DefaultMutableTreeNode
        val treeNode = createTreeNode(archiveModel)
        rootNode.removeAllChildren()
        rootNode.add(treeNode)
        treeModel.reload()

        SwingUtilities.invokeLater { treeView.tree.expandPath(TreePath(treeNode.path)) }
    }

    private fun createTreeNode(baseModel: BaseModel): DefaultMutableTreeNode {
        val treeNode = DefaultMutableTreeNode(baseModel)
        val children = baseModel.children
        Collections.sort(children)
        for (child in children) treeNode.add(createTreeNode(child))
        return treeNode
    }
}
