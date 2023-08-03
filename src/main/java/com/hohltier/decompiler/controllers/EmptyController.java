package com.hohltier.decompiler.controllers;

import com.hohltier.decompiler.views.EmptyView;

public class EmptyController extends BaseController<EmptyView> {

    public EmptyController() {
        super(new EmptyView());
    }

}