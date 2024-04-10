package com.sotasan.decompiler.menus.file;

import com.formdev.flatlaf.extras.components.FlatMenuItem;
import com.sotasan.decompiler.controllers.TabsController;
import com.sotasan.decompiler.services.LanguageService;
import lombok.Getter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class FileCloseTab extends FlatMenuItem implements ActionListener {

    @Getter private static final FileCloseTab INSTANCE = new FileCloseTab();

    private FileCloseTab() {
        addActionListener(this);
        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        setEnabled(false);
        setMnemonic(KeyEvent.VK_W);
        setText(LanguageService.getTranslation("file.closeTab"));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        TabsController.getINSTANCE().closeTab();
    }

}