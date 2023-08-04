package com.hohltier.decompiler.views;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.hohltier.decompiler.services.LanguageService;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.event.KeyEvent;

@Getter
public class EmptyView extends JPanel {

    private final JPanel root;
    private final FlatLabel header;
    private final FlatLabel open;
    private final FlatLabel drag;

    public EmptyView() {
        setLayout(new MigLayout("fill"));

        root = new JPanel();
        root.setLayout(new MigLayout("gapy 15"));
        add(root, "center");

        header = new FlatLabel();
        header.setStyleClass("h1");
        header.setText(LanguageService.getTranslation("empty"));
        root.add(header, "wrap");

        String text = LanguageService.getTranslation("file.openFile");
        String modifier = KeyEvent.getModifiersExText(KeyEvent.CTRL_DOWN_MASK);
        String key = KeyEvent.getKeyText(KeyEvent.VK_O);
        open = new FlatLabel();
        open.setText(String.format("%s (%s+%s)", text, modifier, key));
        root.add(open, "wrap");

        drag = new FlatLabel();
        drag.setText(LanguageService.getTranslation("empty.drag"));
        root.add(drag, "wrap");
    }

}