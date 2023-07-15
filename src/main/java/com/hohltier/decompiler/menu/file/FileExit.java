package com.hohltier.decompiler.menu.file;

import com.hohltier.decompiler.controllers.WindowController;
import com.hohltier.decompiler.services.ResourceService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class FileExit extends JMenuItem implements ActionListener {

    public FileExit() {
        setText(ResourceService.getTranslation("file.exit"));
        setMnemonic(KeyEvent.VK_Q);
        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        addActionListener(this);

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.APP_QUIT_HANDLER)) {
            Desktop.getDesktop().setQuitHandler((e, response) -> actionPerformed(null));
            setVisible(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WindowController.getINSTANCE().dispose();
    }

}