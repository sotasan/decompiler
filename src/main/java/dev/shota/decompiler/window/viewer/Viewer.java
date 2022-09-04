package dev.shota.decompiler.window.viewer;

import javafx.scene.control.TabPane;

public class Viewer extends TabPane {

    private static Viewer INSTANCE;

    private Viewer() {
        setMinWidth(300);
    }

    public static synchronized Viewer getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Viewer();
        return INSTANCE;
    }

}