package com.hohltier.decompiler.controllers;

import com.hohltier.decompiler.views.WindowView;
import lombok.Getter;

public class WindowController extends BaseController<WindowView> {

    @Getter private static final WindowController INSTANCE = new WindowController();

    private WindowController() {
        super(new WindowView());
    }

    public void show() {
        getView().setVisible(true);
    }

    public void activate() {
        getView().setContentPane(getView().getSplitPane());
        getView().validate();
    }

    public void dispose() {
        getView().dispose();
    }

}