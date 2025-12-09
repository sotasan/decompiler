package moe.sota.decompiler.controllers;

import moe.sota.decompiler.views.AboutView;

public class AboutController extends BaseController<AboutView> {

    public AboutController() {
        super(new AboutView());
    }

    public void show() {
        getView().setVisible(true);
    }

}
