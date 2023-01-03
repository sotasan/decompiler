package com.hohltier.decompiler.window.menu;

import com.hohltier.decompiler.window.menu.edit.Edit;
import com.hohltier.decompiler.window.menu.file.File;
import com.hohltier.decompiler.window.menu.view.View;
import com.hohltier.decompiler.window.menu.help.Help;
import com.hohltier.decompiler.window.menu.settings.Settings;

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