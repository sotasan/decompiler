package com.hohltier.decompiler.window.viewer;

import com.hohltier.decompiler.window.utils.Language;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

public class CloseMenu extends ContextMenu {

    private final Tab parent;

    public CloseMenu(Tab parent) {
        this.parent = parent;

        MenuItem close = new MenuItem(Language.get("code.close"));
        MenuItem closeOthers = new MenuItem(Language.get("code.closeOthers"));
        MenuItem closeAll = new MenuItem(Language.get("code.closeAll"));

        //Viewer.INSTANCE.getTabs().addListener((ListChangeListener<Tab>) c -> closeOthers.setDisable(Viewer.INSTANCE.getTabs().size() == 1));

        close.setOnAction(this::onClose);
        closeOthers.setOnAction(this::onCloseOthers);
        closeAll.setOnAction(this::onCloseAll);

        getItems().addAll(close, closeOthers, closeAll);
    }

    private void onClose(ActionEvent event) {
        //Viewer.INSTANCE.getTabs().remove(parent);
    }

    private void onCloseOthers(ActionEvent event) {
        //Viewer.INSTANCE.getTabs().removeIf(tab -> !tab.equals(parent));
    }

    private void onCloseAll(ActionEvent event) {
       //Viewer.INSTANCE.getTabs().clear();
    }

}