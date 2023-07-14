package com.hohltier.decompiler.controllers;

import com.hohltier.decompiler.models.ArchiveModel;
import com.hohltier.decompiler.models.BaseModel;
import com.hohltier.decompiler.views.TreeView;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class ExplorerController extends BaseController<TreeView> {

    @Getter private static final ExplorerController INSTANCE = new ExplorerController();

    private ExplorerController() {
        super(new TreeView());
    }

    public void setArchive(ArchiveModel archiveModel) {
        DefaultTreeModel treeModel = (DefaultTreeModel) getView().getTree().getModel();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) treeModel.getRoot();
        DefaultMutableTreeNode treeNode = createTreeNode(archiveModel);
        rootNode.removeAllChildren();
        rootNode.add(treeNode);
        treeModel.reload();
        getView().getTree().expandPath(new TreePath(treeNode.getPath()));
    }

    private @NotNull DefaultMutableTreeNode createTreeNode(BaseModel baseModel) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(baseModel);
        for (BaseModel child : baseModel.getChildren())
            treeNode.add(createTreeNode(child));
        return treeNode;
    }

}