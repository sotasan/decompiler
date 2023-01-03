package com.hohltier.decompiler.window.menu.view;

import com.hohltier.decompiler.window.utils.Language;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ViewZoomOut extends JMenuItem implements ActionListener {

    public ViewZoomOut() {
        setText(Language.get("view.zoomOut"));
        setMnemonic(KeyEvent.VK_MINUS);
        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        addActionListener(this);
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

}