package com.hohltier.decompiler.views;

import com.formdev.flatlaf.FlatClientProperties;
import com.hohltier.decompiler.controllers.WindowController;
import com.hohltier.decompiler.services.ResourceService;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.lang.management.ManagementFactory;
import java.time.Year;

public class AboutView extends JDialog {

    @Getter private final JPanel root;
    @Getter private final JLabel logo;
    @Getter private final JLabel header;
    @Getter private final JLabel version;
    @Getter private final JPanel vm;
    @Getter private final JLabel copyright;

    public AboutView() {
        super((JFrame) WindowController.getINSTANCE().getComponent());
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_ICON, false);
        setTitle(ResourceService.getTranslation("about"));
        setModal(true);
        setResizable(false);

        // TODO: padding
        root = new JPanel(new MigLayout());
        setContentPane(root);

        // TODO: align top
        logo = new JLabel(new ImageIcon(ResourceService.getLogo().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        header = new JLabel("Decompiler");
        header.putClientProperty("FlatLaf.styleClass", "h1");
        version = new JLabel(String.format(ResourceService.getTranslation("about.version"), ResourceService.getVersion()));

        root.add(logo, "dock west");
        root.add(header, "wrap");
        root.add(version, "wrap");

        vm = new JPanel(new MigLayout());
        vm.setBorder(BorderFactory.createTitledBorder(ResourceService.getTranslation("about.vm")));
        root.add(vm, "wrap, gapy 15px");

        vm.add(new JLabel(ManagementFactory.getRuntimeMXBean().getVmName()), "wrap");
        vm.add(new JLabel(ManagementFactory.getRuntimeMXBean().getVmVersion()), "wrap");
        vm.add(new JLabel(ManagementFactory.getRuntimeMXBean().getVmVendor()), "wrap");

        copyright = new JLabel(String.format("\u00a9 2022 - %s Sota", Year.now().getValue()));
        root.add(copyright, "wrap, gapy 15px");

        // TODO: buttons (github, ok)

        pack();
        setLocationRelativeTo(getOwner());
    }

}