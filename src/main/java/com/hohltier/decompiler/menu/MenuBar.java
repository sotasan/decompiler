package com.hohltier.decompiler.menu;

import com.hohltier.decompiler.menu.help.Help;
import com.hohltier.decompiler.menu.file.File;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar() {
        add(new File());
        add(new Help());
    }

}