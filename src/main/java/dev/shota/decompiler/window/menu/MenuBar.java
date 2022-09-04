package dev.shota.decompiler.window.menu;

import dev.shota.decompiler.window.menu.file.File;
import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar() {
        add(new File());
    }

}