package dev.shota.decompiler.window.menu.edit;

import dev.shota.decompiler.window.utils.Language;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class EditSelectAll extends JMenuItem implements ActionListener {

    public EditSelectAll() {
        setText(Language.get("edit.selectAll"));
        setMnemonic(KeyEvent.VK_A);
        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        addActionListener(this);
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

}