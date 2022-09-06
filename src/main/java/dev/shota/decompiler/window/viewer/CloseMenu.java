package dev.shota.decompiler.window.viewer;

import dev.shota.decompiler.window.utils.Language;
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

        Viewer.INSTANCE.getTabs().addListener((ListChangeListener<Tab>) c -> closeOthers.setDisable(Viewer.INSTANCE.getTabs().size() == 1));

        close.setOnAction(this::close);
        closeOthers.setOnAction(this::closeOthers);
        closeAll.setOnAction(this::closeAll);

        getItems().addAll(close, closeOthers, closeAll);
    }

    private void close(ActionEvent event) {
        Viewer.INSTANCE.getTabs().remove(parent);
    }

    private void closeOthers(ActionEvent event) {
        Viewer.INSTANCE.getTabs().removeIf(tab -> !tab.equals(parent));
    }

    private void closeAll(ActionEvent event) {
        Viewer.INSTANCE.getTabs().clear();
    }

}