package com.hohltier.decompiler.controllers;

import com.hohltier.decompiler.views.TabView;
import com.hohltier.decompiler.views.TabNodeView;
import lombok.Getter;

public class ViewerController extends BaseController<TabView> {

    @Getter private static final ViewerController INSTANCE = new ViewerController();

    private ViewerController() {
        super(new TabView());

        // TODO
        getView().add("Test", new TabNodeView("Test", "package com.hohltier.decompiler;\n" +
                "\n" +
                "public class Main {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello World!\");\n" +
                "    }\n" +
                "\n" +
                "}"));
    }

}