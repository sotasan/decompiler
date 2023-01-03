package com.hohltier.decompiler;

import javax.swing.*;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

public class Window extends JFrame implements DropTargetListener {

    public Window() {
    }

    @Override
    public void dispose() {
        super.dispose();
        System.exit(0);
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