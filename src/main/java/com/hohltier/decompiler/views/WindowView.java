package com.hohltier.decompiler.views;

import com.hohltier.decompiler.controllers.ExplorerController;
import com.hohltier.decompiler.controllers.ViewerController;
import com.hohltier.decompiler.utils.ResourceUtil;
import com.hohltier.decompiler.menu.MenuBar;
import lombok.Getter;
import javax.swing.*;
import java.awt.*;

public class WindowView extends JFrame {

    @Getter private final JSplitPane splitPane;

    public WindowView() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setJMenuBar(new MenuBar());
        setMinimumSize(new Dimension(500, 300));
        setPreferredSize(new Dimension(1000, 600));
        setTitle("Decompiler");

        Image logo = ResourceUtil.getLogo();
        if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.ICON_IMAGE))
            Taskbar.getTaskbar().setIconImage(logo);
        setIconImage(logo);

        splitPane = new JSplitPane();
        splitPane.setDividerLocation(200);
        splitPane.setLeftComponent(ExplorerController.getINSTANCE().getComponent());
        splitPane.setRightComponent(ViewerController.getINSTANCE().getComponent());
        setContentPane(splitPane);

        pack();
        setLocationRelativeTo(null);
    }

}