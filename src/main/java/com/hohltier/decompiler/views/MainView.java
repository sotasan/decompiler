package com.hohltier.decompiler.views;

import com.hohltier.decompiler.controllers.ExplorerController;
import com.hohltier.decompiler.controllers.ViewerController;
import javax.swing.*;

public class MainView extends JSplitPane {

    public MainView() {
        setDividerLocation(200);
        setLeftComponent(ExplorerController.getINSTANCE().getComponent());
        setRightComponent(ViewerController.getINSTANCE().getComponent());
    }

}