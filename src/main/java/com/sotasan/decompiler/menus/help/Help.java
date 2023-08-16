package com.sotasan.decompiler.menus.help;

import com.formdev.flatlaf.extras.components.FlatMenu;
import com.sotasan.decompiler.services.LanguageService;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Help extends FlatMenu {

    public Help() {
        setMnemonic(KeyEvent.VK_H);
        setText(LanguageService.getTranslation("help"));

        add(new HelpAbout());

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.APP_ABOUT))
            setVisible(false);
    }

}