package com.hohltier.decompiler.window.menu.help;

import com.hohltier.decompiler.window.utils.Language;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Help extends JMenu {

    public Help() {
        setText(Language.get("help"));
        setMnemonic(KeyEvent.VK_H);

        add(new HelpAbout());

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.APP_ABOUT))
            setVisible(false);
    }

}