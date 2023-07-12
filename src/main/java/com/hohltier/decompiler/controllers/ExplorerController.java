package com.hohltier.decompiler.controllers;

import com.hohltier.decompiler.views.ExplorerView;
import lombok.Getter;

public class ExplorerController extends BaseController<ExplorerView> {

    @Getter private static final ExplorerController INSTANCE = new ExplorerController();

    private ExplorerController() {
        super(new ExplorerView());
    }

}