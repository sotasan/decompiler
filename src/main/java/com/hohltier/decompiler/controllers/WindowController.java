package com.hohltier.decompiler.controllers;

import com.hohltier.decompiler.views.WindowView;
import lombok.Getter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowController extends BaseController<WindowView> {

    @Getter private static final WindowController INSTANCE = new WindowController();

    private WindowController() {
        super(new WindowView());
        getView().addWindowListener(new WindowListener());
    }

    public void show() {
        getView().setVisible(true);
    }

    public void dispose() {
        getView().dispose();
    }

    private static class WindowListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }

    }

}