package com.hohltier.decompiler.window.menu.settings;

import com.hohltier.decompiler.window.utils.Language;
import javax.swing.*;
import java.awt.event.KeyEvent;

public class Settings extends JMenu {

    public Settings() {
        setText(Language.get("settings"));
        setMnemonic(KeyEvent.VK_S);
    }

}