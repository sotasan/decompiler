package com.sotasan.decompiler.views;

import com.formdev.flatlaf.extras.components.FlatComboBox;
import com.formdev.flatlaf.extras.components.FlatTabbedPane;
import com.formdev.flatlaf.extras.components.FlatToolBar;
import com.sotasan.decompiler.transformers.Transformer;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Getter
public class TabsView extends FlatTabbedPane {

    private final FlatComboBox<Transformer> comboBox;
    private final FlatToolBar toolBar;

    public TabsView() {
        addMouseListener(new TabMouseAdapter());
        setHasFullBorder(true);
        setMinimumSize(new Dimension(250, 0));
        setTabsClosable(true);
        setTabCloseCallback(this::onTabClose);
        setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
        setTabType(TabType.card);

        Dimension dimension = new Dimension(150, 25);
        comboBox = new FlatComboBox<>();
        comboBox.setFocusable(false);
        comboBox.setMaximumSize(dimension);
        comboBox.setModel(new DefaultComboBoxModel<>(Transformer.values()));
        comboBox.setPreferredSize(dimension);
        comboBox.setSelectedItem(Transformer.Vineflower);

        toolBar = new FlatToolBar();
        toolBar.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(comboBox);
        setTrailingComponent(toolBar);
    }

    private void onTabClose(JTabbedPane tabPane, int tabIndex) {
        remove(tabIndex);
    }

    private static class TabMouseAdapter extends MouseAdapter {

        private int x, y;

        @Override
        public void mousePressed(@NotNull MouseEvent event) {
            x = event.getX();
            y = event.getY();
        }

        @Override
        public void mouseReleased(@NotNull MouseEvent event) {
            FlatTabbedPane tabbedPane = (FlatTabbedPane) event.getSource();
            if (event.getButton() == MouseEvent.BUTTON2)
                tabbedPane.remove(tabbedPane.indexAtLocation(x, y));
        }

    }

}