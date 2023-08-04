package com.hohltier.decompiler.menus.file;

import com.formdev.flatlaf.extras.components.FlatMenuItem;
import com.hohltier.decompiler.controllers.WindowController;
import com.hohltier.decompiler.services.LanguageService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class FileExit extends FlatMenuItem implements ActionListener {

    public FileExit() {
        addActionListener(this);
        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        setMnemonic(KeyEvent.VK_Q);
        setText(LanguageService.getTranslation("file.exit"));

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.APP_QUIT_HANDLER)) {
            setVisible(false);
            Desktop.getDesktop().setQuitHandler((e, r) -> actionPerformed(null));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WindowController.getINSTANCE().dispose();
    }

}