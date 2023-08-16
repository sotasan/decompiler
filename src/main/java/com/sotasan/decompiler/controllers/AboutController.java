package com.sotasan.decompiler.controllers;

import com.sotasan.decompiler.views.AboutView;

public class AboutController extends BaseController<AboutView> {

    public AboutController() {
        super(new AboutView());
    }

    public void show() {
        getView().setVisible(true);
    }

}