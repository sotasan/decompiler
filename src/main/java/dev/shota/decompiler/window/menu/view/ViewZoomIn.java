package dev.shota.decompiler.window.menu.view;

import dev.shota.decompiler.window.utils.Language;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ViewZoomIn extends JMenuItem implements ActionListener {

    public ViewZoomIn() {
        setText(Language.get("view.zoomIn"));
        setMnemonic(KeyEvent.VK_PLUS);
        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        addActionListener(this);
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

}