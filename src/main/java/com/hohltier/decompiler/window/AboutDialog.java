package com.hohltier.decompiler.window;

import com.hohltier.decompiler.controllers.WindowController;
import com.hohltier.decompiler.utils.ResourceUtil;
import lombok.SneakyThrows;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;

public class AboutDialog extends JDialog implements ActionListener {

    @SneakyThrows
    public AboutDialog() {
        super(WindowController.getINSTANCE().getView());

        JPanel root = new JPanel(new MigLayout());
        setContentPane(root);

        JLabel logo = new JLabel(new ImageIcon(ResourceUtil.getLogo().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        root.add(logo, "wrap");

        root.add(new JLabel("Decompiler"), "wrap");

        root.add(new JLabel("Version TODO"), "wrap");

        root.add(new JLabel(String.format("\u00a9 2022 - %s Sota", Year.now().getValue())), "wrap");

        setTitle(ResourceUtil.getTranslation("help.about"));
        setModal(true);
        setResizable(false);
        pack();
        setLocationRelativeTo(getOwner());
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }

}