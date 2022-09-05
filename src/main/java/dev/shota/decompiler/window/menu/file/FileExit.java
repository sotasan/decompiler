package dev.shota.decompiler.window.menu.file;

import dev.shota.decompiler.window.Window;
import dev.shota.decompiler.window.utils.Language;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class FileExit extends JMenuItem implements ActionListener {

    public FileExit() {
        setText(Language.get("file.exit"));
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
        Window.INSTANCE.dispose();
    }

}