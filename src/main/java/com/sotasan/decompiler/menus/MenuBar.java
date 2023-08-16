package com.sotasan.decompiler.menus;

import com.formdev.flatlaf.extras.components.FlatMenuBar;
import com.sotasan.decompiler.menus.help.Help;
import com.sotasan.decompiler.menus.file.File;

public class MenuBar extends FlatMenuBar {

    public MenuBar() {
        add(new File());
        add(new Help());
    }

}