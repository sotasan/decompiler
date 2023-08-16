package com.sotasan.decompiler.menus.help;

import com.formdev.flatlaf.extras.components.FlatMenuItem;
import com.sotasan.decompiler.controllers.AboutController;
import com.sotasan.decompiler.services.LanguageService;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class HelpAbout extends FlatMenuItem implements ActionListener {

    public HelpAbout() {
        addActionListener(this);
        setMnemonic(KeyEvent.VK_A);
        setText(LanguageService.getTranslation("about"));

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.APP_ABOUT))
            Desktop.getDesktop().setAboutHandler(e -> actionPerformed(null));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new AboutController().show();
    }

}