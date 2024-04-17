package com.sotasan.decompiler.menus.file;

import com.formdev.flatlaf.extras.components.FlatMenu;
import com.sotasan.decompiler.services.LanguageService;
import javax.swing.*;
import java.awt.event.KeyEvent;

public class File extends FlatMenu {

    public File() {
        setMnemonic(KeyEvent.VK_F);
        setText(LanguageService.getTranslation("file"));

        add(new FileOpenFile());
        add(FileCloseTab.getINSTANCE());
        add(new JSeparator());
        add(new FileNewInstance());
        add(new FileExit());
    }

}