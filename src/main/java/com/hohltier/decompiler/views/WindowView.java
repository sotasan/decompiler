package com.hohltier.decompiler.views;

import com.hohltier.decompiler.utils.ResourceUtil;
import com.hohltier.decompiler.menu.MenuBar;
import javax.swing.*;
import java.awt.*;

public class WindowView extends JFrame {

    public WindowView() {
        Image logo = ResourceUtil.getLogo();
        if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.ICON_IMAGE))
            Taskbar.getTaskbar().setIconImage(logo);
        setIconImage(logo);

        setContentPane(new MainView());
        setMinimumSize(new Dimension(500, 300));
        setPreferredSize(new Dimension(1000, 600));
        setTitle("Decompiler");
        setJMenuBar(new MenuBar());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void dispose() {
        super.dispose();
        System.exit(0);
    }

}