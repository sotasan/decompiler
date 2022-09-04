package dev.shota.decompiler.window.explorer;

import dev.shota.decompiler.runtime.old.singleton.Singleton;
import javafx.scene.control.TreeView;

@Singleton
public class Explorer extends TreeView<String> {

    private Explorer() {
        setMinWidth(100);
    }

}
