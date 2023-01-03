package com.hohltier.decompiler.window.menu.file;

import com.hohltier.decompiler.window.utils.Language;
import javax.swing.*;
import java.awt.event.KeyEvent;

public class File extends JMenu {

    public File() {
        setText(Language.get("file"));
        setMnemonic(KeyEvent.VK_F);

        add(new FileOpenFile());
        add(new FileCloseTab());
        add(new JSeparator());
        add(new FileNewInstance());
        add(new FileExit());
    }

}