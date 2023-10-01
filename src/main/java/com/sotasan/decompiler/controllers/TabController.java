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
        getView().setController(this);
        update();
    }

    public void update() {
        try {
            getView().getTextArea().setText(getText(fileModel));
            if (fileModel.isClass())
                getView().getTextArea().setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        } catch (Exception e) {
            getView().getTextArea().setText(e.getMessage());
            getView().getTextArea().setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        }
        getView().getTextArea().setCaretPosition(0);
    }

    private String getText(@NotNull FileModel fileModel) throws Exception {
        return fileModel.isClass()
                ? TabsController.getINSTANCE().getTransformer().getInstance().transform(fileModel)
                : new String(fileModel.getBytes());
    }

}