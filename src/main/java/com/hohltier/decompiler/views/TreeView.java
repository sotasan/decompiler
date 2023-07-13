package com.hohltier.decompiler.views;

import lombok.Getter;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class TreeView extends JPanel {

    @Getter private final JScrollPane scrollPane;
    @Getter private final JTree tree;

    public TreeView() {
        super(new BorderLayout());

        tree = new JTree(new DefaultMutableTreeNode());
        tree.setRootVisible(false);

        scrollPane = new JScrollPane(tree);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane);
    }

}