package com.hohltier.decompiler.views;

import com.formdev.flatlaf.FlatClientProperties;
import com.hohltier.decompiler.transformers.Transformer;
import lombok.Getter;
import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

public class ViewerView extends JTabbedPane {

    @Getter private final JToolBar toolBar;
    @Getter private final JComboBox<Transformer> types;

    public ViewerView() {
        putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSABLE, true);
        putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSE_CALLBACK, (BiConsumer<JTabbedPane, Integer>) this::onTabClose);
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        Dimension dimension = new Dimension(150, 25);
        types = new JComboBox<>(Transformer.values());
        types.setMaximumSize(dimension);
        types.setPreferredSize(dimension);

        toolBar = new JToolBar();
        toolBar.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(types);
        putClientProperty(FlatClientProperties.TABBED_PANE_TRAILING_COMPONENT, toolBar);
    }

    private void onTabClose(JTabbedPane tabPane, int tabIndex) {
        remove(tabIndex);
    }

}