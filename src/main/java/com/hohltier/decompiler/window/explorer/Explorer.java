package com.hohltier.decompiler.window.explorer;

import javafx.scene.control.TreeView;

public class Explorer extends TreeView<ExplorerEntry> {

    private static Explorer INSTANCE;

    public Explorer() {
        setMinWidth(100);
        setCellFactory(param -> new ExplorerCell());
    }

    public static Explorer getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Explorer();
        return INSTANCE;
    }

}