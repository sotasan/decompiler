package dev.shota.decompiler.window.menu.file;

import dev.shota.decompiler.window.utils.Language;
import javax.swing.*;
import java.awt.event.KeyEvent;

public class File extends JMenu {

    public File() {
        setText(Language.get("file"));
        setMnemonic(KeyEvent.VK_F);

        add(new FileExit());
    }

}