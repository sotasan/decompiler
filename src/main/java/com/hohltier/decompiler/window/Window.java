package com.hohltier.decompiler.window;

import com.hohltier.decompiler.window.viewer.Viewer;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javax.swing.*;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

public class Window extends JFrame implements DropTargetListener {

    public Window() {
        JFXPanel panel = new JFXPanel();
        BorderPane root = new BorderPane();
        root.setCenter(Viewer.getInstance());
        panel.setScene(new Scene(root, 800, 600));
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

}