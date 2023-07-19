package com.hohltier.decompiler.controllers;

import com.hohltier.decompiler.models.FileModel;
import com.hohltier.decompiler.transformers.Transformer;
import com.hohltier.decompiler.views.TabView;
import com.hohltier.decompiler.views.TabEntryView;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewerController extends BaseController<TabView> implements ActionListener {

    @Getter private static final ViewerController INSTANCE = new ViewerController();

    private ViewerController() {
        super(new TabView());
        getView().getComboBox().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        for (int i = 0; i < getView().getTabCount(); i++) {
            TabEntryView tab = (TabEntryView) getView().getComponentAt(i);
            tab.setText(tab.getFileModel());
        }
    }

    public void addTab(@NotNull FileModel fileModel) {
        Component component = null;
        for (int i = 0; i < getView().getTabCount(); i++) {
            Component current = getView().getComponentAt(i);
            if (fileModel == ((TabEntryView) current).getFileModel())
                component = current;
        }

        if (component == null) {
            component = new TabEntryView(fileModel);
            getView().addTab(fileModel.getName(), new ImageIcon(fileModel.getIcon()), component);
        }

        getView().setSelectedComponent(component);
    }

    public void clearTabs() {
        getView().removeAll();
    }

    public Transformer getTransformer() {
        return (Transformer) getView().getComboBox().getSelectedItem();
    }

}