package com.hohltier.decompiler.menu.file;

import com.hohltier.decompiler.utils.ResourceUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class FileCloseTab extends JMenuItem implements ActionListener {

    public FileCloseTab() {
        setText(ResourceUtil.getTranslation("file.closeTab"));
        setMnemonic(KeyEvent.VK_W);
        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        addActionListener(this);
        setEnabled(false);

        //Viewer.INSTANCE.getTabs().addListener((ListChangeListener<Tab>) c -> setEnabled(!Viewer.INSTANCE.getSelectionModel().isEmpty()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Platform.runLater(() -> Viewer.INSTANCE.getTabs().remove(Viewer.INSTANCE.getSelectionModel().getSelectedItem()));
    }

}