package dev.shota.decompiler.window.menu.view;

import dev.shota.decompiler.window.utils.Language;
import javax.swing.*;
import java.awt.event.KeyEvent;

public class View extends JMenu {

    public View() {
        setText(Language.get("view"));
        setMnemonic(KeyEvent.VK_V);

        add(new ViewZoomIn());
        add(new ViewZoomOut());
        add(new ViewZoomReset());
    }

}