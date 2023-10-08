package com.sotasan.decompiler.views;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.sotasan.decompiler.services.LanguageService;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.event.KeyEvent;

@Getter
public class StartView extends JPanel {

    private final JPanel root;
    private final FlatLabel header;
    private final FlatLabel open;
    private final FlatLabel drag;

    public StartView() {
        setLayout(new MigLayout("fill"));

        root = new JPanel();
        root.setLayout(new MigLayout("gapy 15"));
        add(root, "center");

        header = new FlatLabel();
        header.setStyleClass("h1");
        header.setText(LanguageService.getTranslation("empty"));
        root.add(header, "wrap");

        String group = LanguageService.getTranslation("file");
        String item = LanguageService.getTranslation("file.openFile");
        String modifier = KeyEvent.getModifiersExText(KeyEvent.CTRL_DOWN_MASK);
        String key = KeyEvent.getKeyText(KeyEvent.VK_O);
        open = new FlatLabel();
        open.setText(String.format("%s > %s (%s+%s)", group, item, modifier, key));
        root.add(open, "wrap");

        drag = new FlatLabel();
        drag.setText(LanguageService.getTranslation("empty.drag"));
        root.add(drag, "wrap");
    }

}