package dev.shota.decompiler.window.viewer;

import dev.shota.decompiler.runtime.old.singleton.Singleton;
import javafx.scene.control.TabPane;

@Singleton
public class Viewer extends TabPane {

    private Viewer() {
        setMinWidth(300);
    }

}