package com.hohltier.decompiler.views;

import com.hohltier.decompiler.controllers.ViewerController;
import com.hohltier.decompiler.models.BaseModel;
import com.hohltier.decompiler.models.FileModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TreeView extends JPanel {

    @Getter private final JScrollPane scrollPane;
    @Getter private final JTree tree;

    public TreeView() {
        super(new BorderLayout());

        tree = new JTree(new DefaultMutableTreeNode());
        tree.addMouseListener(new TreeMouseAdapter());
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

    private static class TreeMouseAdapter extends MouseAdapter {

        @Override
        public void mouseClicked(@NotNull MouseEvent event) {
            if (event.getClickCount() == 2) {
                TreePath path = ((JTree) event.getSource()).getPathForLocation(event.getX(), event.getY());
                if (path != null) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    if (node.getUserObject() != null) {
                        BaseModel model = (BaseModel) node.getUserObject();
                        if (model instanceof FileModel && model.isClass()) {
                            ViewerController.getINSTANCE().addTab((FileModel) model);
                        }
                    }
                }
            }
        }

    }

}