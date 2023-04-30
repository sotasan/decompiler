package com.hohltier.decompiler.window;

import com.hohltier.decompiler.window.utils.Language;
import com.hohltier.decompiler.window.utils.Styles;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.util.Objects;

public class About extends JDialog implements ActionListener {

    public About() {
        //super(Window.INSTANCE);

        GridPane root = new GridPane();
        root.setId("root");
        root.getStylesheets().addAll(Styles.get("main"), Styles.get("about"));

        ImageView logo = new ImageView(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("logo/logo.png"))));
        logo.setPreserveRatio(true);
        logo.setFitWidth(50);
        root.add(logo, 0, 0);

        VBox content = new VBox();
        Label title = new Label("Decompiler");
        title.setId("title");
        root.add(content, 1, 0);
        content.getChildren().addAll(
                title,
                //new Label(Language.get("about.version") + " " + Updater.getVersion()),
                new Label(String.format("%s 2022 sota", Language.get("about.copyright")))
        );

        HBox controls = new HBox();
        Button github = new Button(Language.get("about.github"));
        Button ok = new Button(Language.get("about.ok"));
        controls.setId("controls");
        ok.setDefaultButton(true);
        github.setOnAction(event -> {
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
                    Desktop.getDesktop().browse(new URI("https://github.com/hohltier/decompiler"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ok.setOnAction(event -> actionPerformed(null));
        controls.getChildren().addAll(github, ok);
        root.add(controls, 1, 1);

        JFXPanel panel = new JFXPanel();
        panel.setScene(new Scene(root));
        JRootPane wrapper = new JRootPane();
        wrapper.setContentPane(panel);
        wrapper.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        add(wrapper);
        setTitle(Language.get("help.about"));
        setModal(true);
        setResizable(false);
        SwingUtilities.invokeLater(() -> {
            pack();
            //setLocationRelativeTo(Window.INSTANCE);
            setVisible(true);
        });
        ok.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }

}