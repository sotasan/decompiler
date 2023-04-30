package com.hohltier.decompiler.window.viewer;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.jetbrains.annotations.NotNull;

public class Viewer extends TabPane implements ListChangeListener<Tab> {

    private static Viewer INSTANCE;

    private Viewer() {
        setMinWidth(300);
        setTabClosingPolicy(TabClosingPolicy.ALL_TABS);
    }

    @Override
    public void onChanged(@NotNull Change<? extends Tab> c) {
        getSelectionModel().select(c.getAddedSubList().get(0));
    }

    public static Viewer getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Viewer();
        return INSTANCE;
    }

}