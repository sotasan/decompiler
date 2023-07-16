package com.hohltier.decompiler.controllers;

import com.hohltier.decompiler.models.FileModel;
import com.hohltier.decompiler.transformers.Transformer;
import com.hohltier.decompiler.views.TabView;
import com.hohltier.decompiler.views.TabNodeView;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;

public class ViewerController extends BaseController<TabView> {

    @Getter private static final ViewerController INSTANCE = new ViewerController();

    private ViewerController() {
        super(new TabView());
    }

    public void addTab(@NotNull FileModel fileModel) {
        Component component = null;
        for (int i = 0; i < getView().getTabCount(); i++) {
            Component current = getView().getComponentAt(i);
            if (fileModel == ((TabNodeView) current).getFileModel())
                component = current;
        }

        if (component == null) {
            component = new TabNodeView(fileModel);
            getView().addTab(fileModel.getName(), new ImageIcon(fileModel.getIcon()), component);
        }

        getView().setSelectedComponent(component);
    }

    public Transformer getTransformer() {
        return (Transformer) getView().getComboBox().getSelectedItem();
    }

}