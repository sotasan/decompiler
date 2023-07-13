package com.hohltier.decompiler.views;

import com.formdev.flatlaf.FlatClientProperties;
import com.hohltier.decompiler.transformers.Transformer;
import lombok.Getter;
import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

public class TabView extends JTabbedPane {

    @Getter private final JToolBar toolBar;
    @Getter private final JComboBox<Transformer> comboBox;

    public TabView() {
        putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSABLE, true);
        putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSE_CALLBACK, (BiConsumer<JTabbedPane, Integer>) this::onTabClose);
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        Dimension dimension = new Dimension(150, 25);
        comboBox = new JComboBox<>(Transformer.values());
        comboBox.setFocusable(false);
        comboBox.setMaximumSize(dimension);
        comboBox.setPreferredSize(dimension);
        comboBox.setSelectedItem(Transformer.Procyon);

        toolBar = new JToolBar();
        toolBar.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(comboBox);
        putClientProperty(FlatClientProperties.TABBED_PANE_TRAILING_COMPONENT, toolBar);
    }

    private void onTabClose(JTabbedPane tabPane, int tabIndex) {
        remove(tabIndex);
    }

}