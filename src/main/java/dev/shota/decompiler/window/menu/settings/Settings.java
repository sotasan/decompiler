package dev.shota.decompiler.window.menu.settings;

import dev.shota.decompiler.window.utils.Language;
import javax.swing.*;
import java.awt.event.KeyEvent;

public class Settings extends JMenu {

    public Settings() {
        setText(Language.get("settings"));
        setMnemonic(KeyEvent.VK_S);
    }

}