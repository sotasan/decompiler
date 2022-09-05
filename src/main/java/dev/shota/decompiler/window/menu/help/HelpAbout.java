package dev.shota.decompiler.window.menu.help;

import dev.shota.decompiler.window.About;
import dev.shota.decompiler.window.utils.Language;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class HelpAbout extends JMenuItem implements ActionListener {

    public HelpAbout() {
        setText(Language.get("help.about"));
        setMnemonic(KeyEvent.VK_A);
        addActionListener(this);

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.APP_ABOUT)) {
            Desktop.getDesktop().setAboutHandler(e -> actionPerformed(null));
            setVisible(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new About();
    }

}