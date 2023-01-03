package com.hohltier.decompiler.window.explorer;

import javafx.scene.control.TreeCell;

public class ExplorerCell extends TreeCell<ExplorerEntry> {

    @Override
    protected void updateItem(ExplorerEntry item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null) {
            setText("");
        } else {
            setText(item.getName());
        }
    }

}