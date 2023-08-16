package com.sotasan.decompiler.menus.file;

import com.formdev.flatlaf.extras.components.FlatMenuItem;
import com.sotasan.decompiler.services.ProcessService;
import com.sotasan.decompiler.services.LanguageService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class FileNewInstance extends FlatMenuItem implements ActionListener {

    public FileNewInstance() {
        addActionListener(this);
        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        setMnemonic(KeyEvent.VK_N);
        setText(LanguageService.getTranslation("file.newInstance"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ProcessService.start();
    }

}