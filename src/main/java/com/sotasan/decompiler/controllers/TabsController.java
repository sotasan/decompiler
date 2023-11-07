package com.sotasan.decompiler.controllers;

import com.sotasan.decompiler.models.FileModel;
import com.sotasan.decompiler.services.ProcessService;
import com.sotasan.decompiler.transformers.Transformer;
import com.sotasan.decompiler.types.ClassType;
import com.sotasan.decompiler.views.TabsView;
import com.sotasan.decompiler.views.TabView;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabsController extends BaseController<TabsView> implements ActionListener {

    @Getter private static final TabsController INSTANCE = new TabsController();

    private TabsController() {
        super(new TabsView());
        getView().getComboBox().addActionListener(this);
        String transformer = ProcessService.PREFERENCES.get("transformer", null);
        if (transformer != null)
            getView().getComboBox().setSelectedItem(Transformer.valueOf(transformer));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        ProcessService.PREFERENCES.put("transformer", getTransformer().name());
        for (int i = 0; i < getView().getTabCount(); i++) {
            TabController controller = ((TabView) getView().getComponentAt(i)).getController();
            if (controller.getFileModel().getType() instanceof ClassType)
                controller.updateAsync();
        }
    }

    public void addTab(FileModel fileModel) {
        TabController controller = getController(fileModel);
        if (controller == null) {
            controller = new TabController(fileModel);
            ImageIcon icon = new ImageIcon(fileModel.getIcon());
            Component component = controller.getComponent();
            controller.updateAsync().thenRun(() -> {
                if (getController(fileModel) == null) {
                    getView().addTab(fileModel.getName(), icon, component);
                    getView().setSelectedComponent(component);
                }
            });
        } else {
            getView().setSelectedComponent(controller.getComponent());
        }
    }

    public void clearTabs() {
        getView().removeAll();
    }

    public Transformer getTransformer() {
        return (Transformer) getView().getComboBox().getSelectedItem();
    }

    private @Nullable TabController getController(FileModel fileModel) {
        TabController controller = null;
        for (int i = 0; i < getView().getTabCount(); i++) {
            TabController current = ((TabView) getView().getComponentAt(i)).getController();
            if (fileModel == current.getFileModel())
                controller = current;
        }
        return controller;
    }

}