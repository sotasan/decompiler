package com.hohltier.decompiler.window.menu.view;

import com.hohltier.decompiler.window.utils.Language;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ViewZoomReset extends JMenuItem implements ActionListener {

    public ViewZoomReset() {
        setText(Language.get("view.zoomReset"));
        setMnemonic(KeyEvent.VK_0);
        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        addActionListener(this);
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

}