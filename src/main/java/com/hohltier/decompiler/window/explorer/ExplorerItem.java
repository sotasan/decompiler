package com.hohltier.decompiler.window.explorer;

import javafx.scene.control.TreeItem;

public class ExplorerItem extends TreeItem<ExplorerEntry> {

    public ExplorerItem(ExplorerEntry entry) {
        setValue(entry);
    }

}