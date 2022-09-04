package dev.shota.decompiler.window.explorer;

import dev.shota.decompiler.reflection.Singleton;
import javafx.scene.control.TreeView;

@Singleton
public class Explorer extends TreeView<String> {

    public Explorer() {
        setMinWidth(100);
    }

}
