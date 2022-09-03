package dev.shota.decompiler.window.explorer;

import javafx.scene.control.TreeView;

public class Explorer extends TreeView<String> {

    private static Explorer INSTANCE;

    private Explorer() {
        setMinWidth(100);
    }

    public static Explorer getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Explorer();
        return INSTANCE;
    }

}
