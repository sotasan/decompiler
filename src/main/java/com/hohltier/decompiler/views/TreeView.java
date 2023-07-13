package com.hohltier.decompiler.views;

import lombok.Getter;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class TreeView extends JPanel {

    @Getter private final JTree tree;
    @Getter private final DefaultMutableTreeNode root;

    public TreeView() {
        super(new BorderLayout());
        root = new DefaultMutableTreeNode("asd");
        tree = new JTree(root);
        tree.setRootVisible(false);
        add(tree);
    }

}