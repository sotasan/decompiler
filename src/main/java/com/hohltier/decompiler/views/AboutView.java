package com.hohltier.decompiler.views;

import com.formdev.flatlaf.FlatClientProperties;
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
    private final JLabel logo;
    private final JLabel header;
    private final JLabel version;
    private final JPanel vm;
    private final JLabel copyright;
    private final JPanel controls;
    private final JButton github;
    private final JButton ok;

    @SneakyThrows
    public AboutView() {
        super((JFrame) WindowController.getINSTANCE().getComponent());
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_ICON, false);
        setTitle(LanguageService.getTranslation("about"));
        setModal(true);
        setResizable(false);

        root = new JPanel(new BorderLayout());
        root.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(root);

        content = new JPanel(new MigLayout());
        content.setBorder(new EmptyBorder(0, 0, 15, 0));
        root.add(content, BorderLayout.CENTER);

        logo = new JLabel(new ImageIcon(getOwner().getIconImages().get(0).getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        logo.setBorder(new EmptyBorder(0, 0, 0, 15));
        logo.setVerticalAlignment(JLabel.TOP);
        content.add(logo, "dock west");

        header = new JLabel("Decompiler");
        header.putClientProperty("FlatLaf.styleClass", "h1");
        content.add(header, "wrap");

        copyright = new JLabel(String.format("\u00a9 2022 - %s Souta", Year.now().getValue()));
        content.add(copyright, "wrap");

        Properties properties = new Properties();
        properties.load(LanguageService.class.getClassLoader().getResourceAsStream("application.properties"));
        version = new JLabel(String.format(LanguageService.getTranslation("about.version"), properties.getProperty("version")));
        content.add(version, "wrap");

        vm = new JPanel(new MigLayout());
        vm.setBorder(BorderFactory.createTitledBorder(LanguageService.getTranslation("about.vm")));
        content.add(vm, "wrap, gapy 15px");

        vm.add(new JLabel(ManagementFactory.getRuntimeMXBean().getVmName()), "wrap");
        vm.add(new JLabel(ManagementFactory.getRuntimeMXBean().getVmVendor()), "wrap");
        vm.add(new JLabel(ManagementFactory.getRuntimeMXBean().getVmVersion()), "wrap");

        controls = new JPanel();
        controls.add(Box.createHorizontalGlue());
        controls.setLayout(new BoxLayout(controls, BoxLayout.X_AXIS));
        root.add(controls, BorderLayout.SOUTH);

        github = new JButton("GitHub");
        github.addActionListener(this::OnGitHubAction);
        github.setFocusable(false);
        controls.add(github);

        controls.add(Box.createHorizontalStrut(5));

        ok = new JButton(LanguageService.getTranslation("about.ok"));
        ok.addActionListener(this::onOkAction);
        controls.add(ok);
        getRootPane().setDefaultButton(ok);

        pack();
        setLocationRelativeTo(getOwner());
    }

    @SneakyThrows
    private void OnGitHubAction(ActionEvent event) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
            Desktop.getDesktop().browse(new URI("https://github.com/hohltier/decompiler"));
    }

    private void onOkAction(ActionEvent event) {
        dispose();
    }

}