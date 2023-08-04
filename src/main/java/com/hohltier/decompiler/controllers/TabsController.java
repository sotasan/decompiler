package com.hohltier.decompiler.controllers;

import com.hohltier.decompiler.models.FileModel;
import com.hohltier.decompiler.transformers.Transformer;
import com.hohltier.decompiler.views.TabsView;
import com.hohltier.decompiler.views.TabView;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabsController extends BaseController<TabsView> implements ActionListener {

    @Getter private static final TabsController INSTANCE = new TabsController();

    private TabsController() {
        super(new TabsView());
        getView().getComboBox().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        for (int i = 0; i < getView().getTabCount(); i++)
            ((TabView) getView().getComponentAt(i)).getController().update();
    }

    public void addTab(@NotNull FileModel fileModel) {
        TabController controller = null;
        for (int i = 0; i < getView().getTabCount(); i++) {
            TabController current = ((TabView) getView().getComponentAt(i)).getController();
            if (fileModel == current.getFileModel())
                controller = current;
        }

        if (controller == null) {
            controller = new TabController(fileModel);
            getView().addTab(fileModel.getName(), new ImageIcon(fileModel.getIcon()), controller.getComponent());
        }

        getView().setSelectedComponent(controller.getComponent());
    }

    public void clearTabs() {
        getView().removeAll();
    }

    public Transformer getTransformer() {
        return (Transformer) getView().getComboBox().getSelectedItem();
    }

}