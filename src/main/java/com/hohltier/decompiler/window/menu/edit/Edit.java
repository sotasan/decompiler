package com.hohltier.decompiler.window.menu.edit;

import com.hohltier.decompiler.window.utils.Language;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Edit extends JMenu {

    public Edit() {
        setText(Language.get("edit"));
        setMnemonic(KeyEvent.VK_E);

        add(new EditCopy());
        add(new EditSelectAll());
    }

}