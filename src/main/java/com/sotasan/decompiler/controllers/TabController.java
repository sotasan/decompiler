package com.sotasan.decompiler.controllers;

import com.sotasan.decompiler.models.FileModel;
import com.sotasan.decompiler.views.TabView;
import lombok.Getter;

@Getter
public class TabController extends BaseController<TabView> {

    private final FileModel fileModel;

    public TabController(FileModel fileModel) {
        super(new TabView());
        this.fileModel = fileModel;
        getView().setController(this);
        update();
    }

    public void update() {
        try {
            getView().getTextArea().setText(getText(fileModel));
            getView().getTextArea().setCaretPosition(0);
        } catch (Exception e) {
            getView().getTextArea().setText(e.getMessage());
        }
    }

    private String getText(FileModel fileModel) throws Exception {
        if (fileModel.isClass()) {
            return TabsController.getINSTANCE().getTransformer().getInstance().transform(fileModel);
        }
        return new String(fileModel.getBytes());
    }

}