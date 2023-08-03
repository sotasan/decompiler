package com.hohltier.decompiler.views;

import com.hohltier.decompiler.services.LanguageService;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.event.KeyEvent;

@Getter
public class EmptyView extends JPanel {

    private final JPanel root;
    private final JLabel header;
    private final JLabel open;
    private final JLabel drag;

    public EmptyView() {
        setLayout(new MigLayout("fill"));

        root = new JPanel(new MigLayout("gapy 15"));
        add(root, "center");

        header = new JLabel(LanguageService.getTranslation("empty"));
        header.putClientProperty("FlatLaf.styleClass", "h1");
        root.add(header, "wrap");

        String text = LanguageService.getTranslation("file.openFile");
        String modifier = KeyEvent.getModifiersExText(KeyEvent.CTRL_DOWN_MASK);
        String key = KeyEvent.getKeyText(KeyEvent.VK_O);
        open = new JLabel(String.format("%s (%s+%s)", text, modifier, key));
        root.add(open, "wrap");

        drag = new JLabel(LanguageService.getTranslation("empty.drag"));
        root.add(drag, "wrap");
    }

}