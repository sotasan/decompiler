package moe.sota.decompiler.controllers;

import lombok.Getter;
import moe.sota.decompiler.models.ArchiveModel;
import moe.sota.decompiler.models.BaseModel;
import moe.sota.decompiler.views.TreeView;
import org.jetbrains.annotations.NotNull;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.Collections;
import java.util.List;

public class TreeController extends BaseController<TreeView> {

    @Getter
    private static final TreeController INSTANCE = new TreeController();

    private TreeController() {
        super(new TreeView());
    }

    public void setArchive(ArchiveModel archiveModel) {
        DefaultTreeModel treeModel = (DefaultTreeModel) getView().getTree().getModel();
        DefaultMutableTreeNode treeNode = createTreeNode(archiveModel);
        treeModel.setRoot(treeNode);
        treeModel.reload();

        SwingUtilities.invokeLater(() -> {
            var tree = getView().getTree();
            TreePath topPath = new TreePath(treeNode.getPath());

            tree.expandPath(topPath);

            if (tree.getRowCount() > 0) {
                tree.expandRow(0);
                tree.collapseRow(0);
                tree.expandPath(topPath);
            }

            tree.revalidate();
            tree.repaint();
        });
    }

    private @NotNull DefaultMutableTreeNode createTreeNode(BaseModel baseModel) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(baseModel);
        List<BaseModel> children = baseModel.getChildren();
        Collections.sort(children);
        for (BaseModel child : children)
            treeNode.add(createTreeNode(child));
        return treeNode;
    }

}