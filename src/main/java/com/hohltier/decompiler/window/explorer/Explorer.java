package com.hohltier.decompiler.window.explorer;

import javafx.scene.control.TreeView;

public class Explorer extends TreeView<ExplorerEntry> {

    public Explorer() {
        setMinWidth(100);
        setCellFactory(param -> new ExplorerCell());
    }

}