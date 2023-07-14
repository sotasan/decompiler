package com.hohltier.decompiler.controllers;

import com.hohltier.decompiler.models.FileModel;
import com.hohltier.decompiler.views.TabView;
import com.hohltier.decompiler.views.TabNodeView;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;

public class ViewerController extends BaseController<TabView> {

    @Getter private static final ViewerController INSTANCE = new ViewerController();

    private ViewerController() {
        super(new TabView());

        // TODO
        addTab(new FileModel("Test.class"), "package com.hohltier.decompiler;\n" +
                "\n" +
                "public class Main {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello World!\");\n" +
                "    }\n" +
                "\n" +
                "}");
    }

    // TODO
    public void addTab(@NotNull FileModel fileModel, String text) {
        getView().addTab(fileModel.getName(), new ImageIcon(fileModel.getIcon()), new TabNodeView(fileModel.getName(), text));
    }

}