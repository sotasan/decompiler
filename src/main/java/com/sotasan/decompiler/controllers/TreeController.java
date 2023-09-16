package com.sotasan.decompiler.controllers;

import com.sotasan.decompiler.models.ArchiveModel;
import com.sotasan.decompiler.models.BaseModel;
import com.sotasan.decompiler.views.TreeView;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.Collections;
import java.util.List;

public class TreeController extends BaseController<TreeView> {

    @Getter private static final TreeController INSTANCE = new TreeController();

    private TreeController() {
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
        List<BaseModel> children = baseModel.getChildren();
        Collections.sort(children);
        children.forEach(child -> treeNode.add(createTreeNode(child)));
        return treeNode;
    }

}