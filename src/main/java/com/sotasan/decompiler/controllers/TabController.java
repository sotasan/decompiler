package com.sotasan.decompiler.controllers;

import com.sotasan.decompiler.models.FileModel;
import com.sotasan.decompiler.types.ClassType;
import com.sotasan.decompiler.types.Type;
import com.sotasan.decompiler.views.TabView;
import lombok.Getter;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.jetbrains.annotations.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@Getter
public class TabController extends BaseController<TabView> {

    private final FileModel fileModel;

    public TabController(@NotNull FileModel fileModel) {
        super(new TabView());
        this.fileModel = fileModel;
        getView().setController(this);
    }

    public CompletableFuture<Void> update() {
        try {
            return getText(fileModel).thenAccept(s -> {
                getView().getTextArea().setText(s);
                Type type = fileModel.getType();
                if (type != null)
                    getView().getTextArea().setSyntaxEditingStyle(type.getSyntax());
                getView().getTextArea().setCaretPosition(0);
            });
        } catch (Exception e) {
            getView().getTextArea().setText(e.getMessage());
            getView().getTextArea().setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
            getView().getTextArea().setCaretPosition(0);
            return CompletableFuture.completedFuture(null);
        }
    }

    private CompletableFuture<String> getText(@NotNull FileModel fileModel) throws Exception {
        return fileModel.getType() instanceof ClassType
                ? TabsController.getINSTANCE().getTransformer().newInstance().transform(fileModel)
                : CompletableFuture.completedFuture(new String(fileModel.getBytes(), StandardCharsets.UTF_8));
    }

}