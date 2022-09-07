package dev.shota.decompiler.window.menu.file;

import dev.shota.decompiler.window.utils.Language;
import dev.shota.decompiler.window.viewer.Viewer;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Tab;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class FileCloseTab extends JMenuItem implements ActionListener {

    public FileCloseTab() {
        setText(Language.get("file.closeTab"));
        setMnemonic(KeyEvent.VK_W);
        setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        addActionListener(this);
        setEnabled(false);

        Platform.runLater(() -> Viewer.INSTANCE.getTabs().addListener((ListChangeListener<Tab>) c -> setEnabled(!Viewer.INSTANCE.getSelectionModel().isEmpty())));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Platform.runLater(() -> Viewer.INSTANCE.getTabs().remove(Viewer.INSTANCE.getSelectionModel().getSelectedItem()));
    }

}