package dev.shota.decompiler.window.viewer;

import com.formdev.flatlaf.util.SystemInfo;
import dev.shota.decompiler.reflection.Singleton;
import javafx.scene.control.TabPane;

@Singleton
public class Viewer extends TabPane {

    private Viewer() {
        setMinWidth(300);
        setTabClosingPolicy(TabClosingPolicy.ALL_TABS);
        if (!(SystemInfo.isMacOS && SystemInfo.isMacFullWindowContentSupported))
            setTabDragPolicy(TabDragPolicy.REORDER);
    }

}