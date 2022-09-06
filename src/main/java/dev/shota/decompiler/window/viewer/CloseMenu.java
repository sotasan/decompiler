package dev.shota.decompiler.window.viewer;

import dev.shota.decompiler.window.utils.Language;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

public class CloseMenu extends ContextMenu {

    public CloseMenu(Tab parent) {
        MenuItem close = new MenuItem(Language.get("code.close"));
        MenuItem closeOthers = new MenuItem(Language.get("code.closeOthers"));
        MenuItem closeAll = new MenuItem(Language.get("code.closeAll"));

        Viewer.INSTANCE.getTabs().addListener((ListChangeListener<Tab>) c -> closeOthers.setDisable(Viewer.INSTANCE.getTabs().size() == 1));

        close.setOnAction(event -> Viewer.INSTANCE.getTabs().remove(parent));
        closeOthers.setOnAction(event -> Viewer.INSTANCE.getTabs().removeIf(tab -> !tab.equals(parent)));
        closeAll.setOnAction(event -> Viewer.INSTANCE.getTabs().clear());

        getItems().addAll(close, closeOthers, closeAll);
    }

}