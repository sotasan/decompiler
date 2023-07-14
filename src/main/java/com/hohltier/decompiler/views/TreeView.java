package com.hohltier.decompiler.views;

import com.hohltier.decompiler.models.BaseModel;
import lombok.Getter;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

public class TreeView extends JPanel {

    @Getter private final JScrollPane scrollPane;
    @Getter private final JTree tree;

    public TreeView() {
        super(new BorderLayout());

        tree = new JTree(new DefaultMutableTreeNode());
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setCellRenderer(new TreeCellRenderer());
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);

        scrollPane = new JScrollPane(tree);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane);
    }

    private static class TreeCellRenderer extends DefaultTreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean focused) {
            Component component = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, focused);
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            if (node.getUserObject() != null) {
                BaseModel model = (BaseModel) node.getUserObject();
                setText(model.getName());
                setIcon(new ImageIcon(model.getIcon()));
            }
            return component;
        }

    }

}