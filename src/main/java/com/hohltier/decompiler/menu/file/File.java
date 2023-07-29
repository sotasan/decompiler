package com.hohltier.decompiler.menu.file;

import com.hohltier.decompiler.services.LanguageService;
import javax.swing.*;
import java.awt.event.KeyEvent;

public class File extends JMenu {

    public File() {
        setText(LanguageService.getTranslation("file"));
        setMnemonic(KeyEvent.VK_F);

        add(new FileOpenFile());
        add(new JSeparator());
        add(new FileNewInstance());
        add(new FileExit());
    }

}