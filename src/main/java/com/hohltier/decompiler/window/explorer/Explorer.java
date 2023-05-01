package com.hohltier.decompiler.window.explorer;

import com.hohltier.decompiler.window.Window;
import com.hohltier.decompiler.window.viewer.Viewer;
import javafx.scene.control.TreeView;

public class Explorer extends TreeView<ExplorerEntry> {

    private static Explorer INSTANCE;

    public Explorer() {
        setMinWidth(100);
        setCellFactory(param -> new ExplorerCell());
    }

    public void setEnabled(boolean value) {
        if (value)
            Window.getInstance().getRoot().getItems().add(this);
        Window.getInstance().getRoot().getItems().add(Viewer.getInstance());
    }

    public static Explorer getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Explorer();
        return INSTANCE;
    }

}