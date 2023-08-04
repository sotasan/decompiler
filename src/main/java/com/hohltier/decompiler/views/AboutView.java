package com.hohltier.decompiler.views;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.hohltier.decompiler.controllers.WindowController;
import com.hohltier.decompiler.services.LanguageService;
import lombok.Getter;
import lombok.SneakyThrows;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.time.Year;
import java.util.Properties;

@Getter
public class AboutView extends JDialog {

    private final JPanel root;
    private final JPanel content;
    private final FlatLabel logo;
    private final FlatLabel header;
    private final FlatLabel copyright;
    private final FlatLabel version;
    private final JPanel vm;
    private final JPanel controls;
    private final FlatButton github;
    private final FlatButton ok;

    @SneakyThrows
    public AboutView() {
        super((JFrame) WindowController.getINSTANCE().getComponent());
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_ICON, false);
        setModal(true);
        setResizable(false);
        setTitle(LanguageService.getTranslation("about"));

        root = new JPanel();
        root.setBorder(new EmptyBorder(15, 15, 15, 15));
        root.setLayout(new BorderLayout());
        setContentPane(root);

        content = new JPanel();
        content.setBorder(new EmptyBorder(0, 0, 15, 0));
        content.setLayout(new MigLayout());
        root.add(content, BorderLayout.CENTER);

        logo = new FlatLabel();
        logo.setBorder(new EmptyBorder(0, 0, 0, 15));
        logo.setIcon(new ImageIcon(getOwner().getIconImages().get(0).getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        logo.setVerticalAlignment(JLabel.TOP);
        content.add(logo, "dock west");

        header = new FlatLabel();
        header.setStyleClass("h1");
        header.setText("Decompiler");
        content.add(header, "wrap");

        copyright = new FlatLabel();
        copyright.setText(String.format("\u00a9 2022 - %s S\u014Dta", Year.now().getValue()));
        content.add(copyright, "wrap");

        Properties properties = new Properties();
        properties.load(LanguageService.class.getClassLoader().getResourceAsStream("application.properties"));
        version = new FlatLabel();
        version.setText(String.format(LanguageService.getTranslation("about.version"), properties.getProperty("version")));
        content.add(version, "wrap");

        vm = new JPanel();
        vm.setBorder(BorderFactory.createTitledBorder(LanguageService.getTranslation("about.vm")));
        vm.setLayout(new MigLayout());
        content.add(vm, "wrap, gapy 15px");

        // TODO: FlatLabel
        vm.add(new JLabel(ManagementFactory.getRuntimeMXBean().getVmName()), "wrap");
        vm.add(new JLabel(ManagementFactory.getRuntimeMXBean().getVmVendor()), "wrap");
        vm.add(new JLabel(ManagementFactory.getRuntimeMXBean().getVmVersion()), "wrap");

        controls = new JPanel();
        controls.add(Box.createHorizontalGlue());
        controls.setLayout(new BoxLayout(controls, BoxLayout.X_AXIS));
        root.add(controls, BorderLayout.SOUTH);

        github = new FlatButton();
        github.addActionListener(this::onGitHubAction);
        github.setFocusable(false);
        github.setText("GitHub");
        controls.add(github);

        controls.add(Box.createHorizontalStrut(5));

        ok = new FlatButton();
        ok.addActionListener(this::onOkAction);
        ok.setText(LanguageService.getTranslation("about.ok"));
        controls.add(ok);
        getRootPane().setDefaultButton(ok);

        pack();
        setLocationRelativeTo(getOwner());
    }

    @SneakyThrows
    private void onGitHubAction(ActionEvent event) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
            Desktop.getDesktop().browse(new URI("https://github.com/hohltier/decompiler"));
    }

    private void onOkAction(ActionEvent event) {
        dispose();
    }

}