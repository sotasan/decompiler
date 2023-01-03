package com.hohltier.decompiler.window.menu.file;

import com.hohltier.decompiler.loader.FileLoader;
import com.hohltier.decompiler.window.Window;
import com.hohltier.decompiler.window.utils.Language;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

public class FileOpenFile extends JMenuItem implements ActionListener {

    public FileOpenFile() {
        setText(Language.get("file.openFile"));
        setMnemonic(KeyEvent.VK_O);
        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(getText());
        fileChooser.setFileFilter(new FileNameExtensionFilter(Language.get("file.openFile.extension"), "jar", "war", "zip", "class"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.showOpenDialog(Window.INSTANCE);
        FileLoader.load(List.of(fileChooser.getSelectedFiles()));
    }

}