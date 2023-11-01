package com.sotasan.decompiler.controllers;

import com.sotasan.decompiler.models.FileModel;
import com.sotasan.decompiler.types.ClassType;
import com.sotasan.decompiler.types.Type;
import com.sotasan.decompiler.views.TabView;
import lombok.Getter;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Getter
public class TabController extends BaseController<TabView> {

    private final FileModel fileModel;

    public TabController(@NotNull FileModel fileModel) {
        super(new TabView());
        this.fileModel = fileModel;
        getView().setController(this);
    }

    public CompletableFuture<Void> updateAsync() {
        return getTextAsync(fileModel)
                .thenAccept(s -> {
                    getView().getTextArea().setText(s);
                    Type type = fileModel.getType();
                    if (type != null)
                        getView().getTextArea().setSyntaxEditingStyle(type.getSyntax());
                })
                .exceptionally(e -> {
                    StringWriter stringWriter = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(stringWriter);
                    e.printStackTrace(printWriter);
                    getView().getTextArea().setText(stringWriter.toString().trim());
                    getView().getTextArea().setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
                    return null;
                })
                .thenRun(() -> {
                    getView().getScrollPane().getVerticalScrollBar().setValue(0);
                    getView().getScrollPane().getHorizontalScrollBar().setValue(0);
                });
    }

    @Contract("_ -> new")
    private @NotNull CompletableFuture<String> getTextAsync(FileModel fileModel) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return fileModel.getType() instanceof ClassType
                        ? TabsController.getINSTANCE().getTransformer().newInstance().transform(fileModel)
                        : new String(fileModel.getBytes(), StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

}