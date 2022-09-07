package dev.shota.decompiler.window.menu;

import dev.shota.decompiler.window.menu.edit.Edit;
import dev.shota.decompiler.window.menu.file.File;
import dev.shota.decompiler.window.menu.help.Help;
import dev.shota.decompiler.window.menu.settings.Settings;
import dev.shota.decompiler.window.menu.view.View;
import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar() {
        add(new File());
        add(new Edit());
        add(new View());
        add(new Settings());
        add(new Help());
    }

}