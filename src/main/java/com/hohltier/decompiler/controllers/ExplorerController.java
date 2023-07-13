package com.hohltier.decompiler.controllers;

import com.hohltier.decompiler.models.ArchiveModel;
import com.hohltier.decompiler.views.TreeView;
import lombok.Getter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class ExplorerController extends BaseController<TreeView> {

    @Getter private static final ExplorerController INSTANCE = new ExplorerController();

    private ExplorerController() {
        super(new TreeView());
    }

    public void setArchive(ArchiveModel archiveModel) {
        DefaultTreeModel model = (DefaultTreeModel) getView().getTree().getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.removeAllChildren();
        root.add(archiveModel);
        model.reload();
        getView().getTree().expandPath(new TreePath(archiveModel.getPath()));
    }

}