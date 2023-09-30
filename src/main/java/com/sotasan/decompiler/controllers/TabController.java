package com.sotasan.decompiler.controllers;

import com.sotasan.decompiler.models.FileModel;
import com.sotasan.decompiler.views.TabView;
import lombok.Getter;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.jetbrains.annotations.NotNull;

@Getter
public class TabController extends BaseController<TabView> {

    private final FileModel fileModel;

    public TabController(@NotNull FileModel fileModel) {
        super(new TabView());
        this.fileModel = fileModel;
        if (fileModel.isClass())
            getView().getTextArea().setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
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