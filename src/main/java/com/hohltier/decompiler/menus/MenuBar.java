package com.hohltier.decompiler.menus;

import com.formdev.flatlaf.extras.components.FlatMenuBar;
import com.hohltier.decompiler.menus.help.Help;
import com.hohltier.decompiler.menus.file.File;

public class MenuBar extends FlatMenuBar {

    public MenuBar() {
        add(new File());
        add(new Help());
    }

}