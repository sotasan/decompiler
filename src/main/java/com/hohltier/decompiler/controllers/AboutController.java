package com.hohltier.decompiler.controllers;

import com.hohltier.decompiler.views.AboutView;

public class AboutController extends BaseController<AboutView> {

    public AboutController() {
        super(new AboutView());
    }

    public void show() {
        getView().setVisible(true);
    }

}