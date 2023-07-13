package com.hohltier.decompiler.controllers;

import com.formdev.flatlaf.FlatClientProperties;
import com.hohltier.decompiler.views.ViewerView;
import com.hohltier.decompiler.views.ViewerEntryView;
import lombok.Getter;
import javax.swing.*;
import java.util.function.BiConsumer;

public class ViewerController extends BaseController<ViewerView> {

    @Getter private static final ViewerController INSTANCE = new ViewerController();

    private ViewerController() {
        super(new ViewerView());
        getView().putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSE_CALLBACK, (BiConsumer<JTabbedPane, Integer>) this::onTabClose);

        // TODO
        getView().add("Test", new ViewerEntryView("Test", "package com.hohltier.decompiler;\n" +
                "\n" +
                "public class Main {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello World!\");\n" +
                "    }\n" +
                "\n" +
                "}"));
    }

    private void onTabClose(JTabbedPane tabPane, int tabIndex) {
        getView().remove(tabIndex);
    }

}