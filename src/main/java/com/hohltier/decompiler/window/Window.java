package com.hohltier.decompiler.window;

import com.hohltier.decompiler.window.explorer.Explorer;
import com.hohltier.decompiler.window.utils.Styles;
import com.hohltier.decompiler.window.viewer.Viewer;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import lombok.Getter;
import javax.swing.*;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

public class Window extends JFrame implements DropTargetListener {

    private static Window INSTANCE;
    @Getter private final SplitPane root;

    public Window() {
        JFXPanel panel = new JFXPanel();

        root = new SplitPane();
        SplitPane.setResizableWithParent(Explorer.getInstance(), false);
        root.setDividerPositions(
                Explorer.getInstance().getMinWidth() / (Explorer.getInstance().getMinWidth() + Viewer.getInstance().getMinWidth()),
                Viewer.getInstance().getMinWidth() / (Explorer.getInstance().getMinWidth() + Viewer.getInstance().getMinWidth())
        );

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(Styles.get("window"));
        panel.setScene(scene);
        add(panel);

        pack();
        setTitle("Decompiler");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void dragEnter(DropTargetDragEvent event) {
    }

    @Override
    public void dragOver(DropTargetDragEvent event) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent event) {
    }

    @Override
    public void dragExit(DropTargetEvent event) {
    }

    @Override
    public void drop(DropTargetDropEvent event) {
    }

    public static Window getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Window();
        return INSTANCE;
    }

}