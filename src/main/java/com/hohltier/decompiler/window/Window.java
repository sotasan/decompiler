package com.hohltier.decompiler.window;

import com.hohltier.decompiler.window.explorer.Explorer;
import com.hohltier.decompiler.window.menu.MenuBar;
import com.hohltier.decompiler.utils.ResourceUtil;
import com.hohltier.decompiler.window.viewer.Viewer;
import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private static Window INSTANCE;

    public Window() {
        Image logo = ResourceUtil.getLogo();
        if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.ICON_IMAGE))
            Taskbar.getTaskbar().setIconImage(logo);
        setIconImage(logo);

        JSplitPane root = new JSplitPane();
        root.setDividerLocation(200);
        root.setLeftComponent(Explorer.getInstance());
        root.setRightComponent(Viewer.getInstance());
        setContentPane(root);

        setMinimumSize(new Dimension(500, 300));
        setPreferredSize(new Dimension(1000, 600));
        setTitle("Decompiler");
        setJMenuBar(new MenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void dispose() {
        super.dispose();
        System.exit(0);
    }

    public static Window getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Window();
        return INSTANCE;
    }

}