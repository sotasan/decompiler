package com.hohltier.decompiler.menu.file;

import com.hohltier.decompiler.services.ProcessService;
import com.hohltier.decompiler.services.ResourceService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class FileNewInstance extends JMenuItem implements ActionListener {

    public FileNewInstance() {
        setText(ResourceService.getTranslation("file.newInstance"));
        setMnemonic(KeyEvent.VK_N);
        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ProcessService.start();
    }

}