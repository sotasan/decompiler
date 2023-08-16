package com.sotasan.decompiler.menus.file;

import com.formdev.flatlaf.extras.components.FlatMenuItem;
import com.sotasan.decompiler.controllers.WindowController;
import com.sotasan.decompiler.services.LoaderService;
import com.sotasan.decompiler.services.LanguageService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class FileOpenFile extends FlatMenuItem implements ActionListener {

    private final String text;

    public FileOpenFile() {
        text = LanguageService.getTranslation("file.openFile");
        addActionListener(this);
        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        setMnemonic(KeyEvent.VK_O);
        setText(String.format("%s...", text));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle(text);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Java (*.jar;*.war;*.zip)", "jar", "war", "zip"));
        fileChooser.showOpenDialog(WindowController.getINSTANCE().getComponent());
        if (fileChooser.getSelectedFile() != null)
            LoaderService.load(fileChooser.getSelectedFile());
    }

}