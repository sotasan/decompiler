package com.hohltier.decompiler.views;

import com.formdev.flatlaf.FlatClientProperties;
import com.hohltier.decompiler.transformers.Transformer;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.BiConsumer;

@Getter
public class TabView extends JTabbedPane {

    private final JToolBar toolBar;
    private final JComboBox<Transformer> comboBox;

    public TabView() {
        addMouseListener(new TabMouseAdapter());
        putClientProperty(FlatClientProperties.TABBED_PANE_HAS_FULL_BORDER, true);
        putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSABLE, true);
        putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSE_CALLBACK, (BiConsumer<JTabbedPane, Integer>) this::onTabClose);
        putClientProperty(FlatClientProperties.TABBED_PANE_TAB_TYPE, FlatClientProperties.TABBED_PANE_TAB_TYPE_CARD);
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        Dimension dimension = new Dimension(150, 25);
        comboBox = new JComboBox<>(Transformer.values());
        comboBox.setFocusable(false);
        comboBox.setMaximumSize(dimension);
        comboBox.setPreferredSize(dimension);
        comboBox.setSelectedItem(Transformer.Fernflower);

        toolBar = new JToolBar();
        toolBar.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(comboBox);
        putClientProperty(FlatClientProperties.TABBED_PANE_TRAILING_COMPONENT, toolBar);
    }

    private void onTabClose(JTabbedPane tabPane, int tabIndex) {
        remove(tabIndex);
    }

    private static class TabMouseAdapter extends MouseAdapter {

        @Override
        public void mouseClicked(@NotNull MouseEvent event) {
            JTabbedPane tabbedPane = (JTabbedPane) event.getSource();
            if (event.getButton() == MouseEvent.BUTTON2)
                tabbedPane.remove(tabbedPane.indexAtLocation(event.getX(), event.getY()));
        }

    }

}