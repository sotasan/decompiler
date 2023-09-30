package com.sotasan.decompiler.views;

import com.formdev.flatlaf.extras.components.FlatScrollPane;
import com.formdev.flatlaf.extras.components.FlatTree;
import com.sotasan.decompiler.controllers.TabsController;
import com.sotasan.decompiler.models.BaseModel;
import com.sotasan.decompiler.models.FileModel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Getter
public class TreeView extends JPanel {

    private final JTree tree;
    private final FlatScrollPane scrollPane;

    public TreeView() {
        super(new BorderLayout());

        // TODO: only one root node
        tree = new FlatTree();
        tree.addMouseListener(new TreeMouseAdapter());
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setCellRenderer(new TreeCellRenderer());
        tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode()));
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);

        scrollPane = new FlatScrollPane();
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setViewportView(tree);
        add(scrollPane);
    }

    private static class TreeCellRenderer extends DefaultTreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean focused) {
            Component component = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, focused);
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            if (node.getUserObject() instanceof BaseModel) {
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
            if (event.getClickCount() != 2)
                return;

            TreePath path = ((JTree) event.getSource()).getSelectionPath();
            if (path == null)
                return;

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            if (node.getUserObject() == null)
                return;

            BaseModel model = (BaseModel) node.getUserObject();
            if (model instanceof FileModel)
                TabsController.getINSTANCE().addTab((FileModel) model);
        }

    }

}