package com.hohltier.decompiler.controllers;

import com.hohltier.decompiler.models.ArchiveModel;
import com.hohltier.decompiler.views.TreeView;
import lombok.Getter;
import javax.swing.tree.DefaultMutableTreeNode;

public class ExplorerController extends BaseController<TreeView> {

    @Getter private static final ExplorerController INSTANCE = new ExplorerController();

    private ExplorerController() {
        super(new TreeView());

        DefaultMutableTreeNode test = new ArchiveModel("TODO");
        getView().getRoot().add(test);
    }

}