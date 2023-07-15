package com.hohltier.decompiler.menu.help;

import com.hohltier.decompiler.services.ResourceService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Help extends JMenu {

    public Help() {
        setText(ResourceService.getTranslation("help"));
        setMnemonic(KeyEvent.VK_H);

        add(new HelpAbout());

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.APP_ABOUT))
            setVisible(false);
    }

}