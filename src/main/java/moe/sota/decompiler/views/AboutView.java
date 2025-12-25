package moe.sota.decompiler.views;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatLabel;
import lombok.Getter;
import lombok.SneakyThrows;
import moe.sota.decompiler.controllers.StartController;
import moe.sota.decompiler.controllers.WindowController;
import moe.sota.decompiler.services.LanguageService;
import net.miginfocom.swing.MigLayout;
import org.koin.java.KoinJavaComponent;

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

    private final LanguageService languageService = KoinJavaComponent.get(LanguageService.class);

    private final JPanel root;
    private final JPanel content;
    private final FlatLabel logo;
    private final FlatLabel header;
    private final FlatLabel copyright;
    private final FlatLabel version;
    private final JPanel vm;
    private final FlatLabel vmName;
    private final FlatLabel vmVendor;
    private final FlatLabel vmVersion;
    private final JPanel controls;
    private final FlatButton github;
    private final FlatButton ok;

    @SneakyThrows
    public AboutView() {
        super((JFrame) (((WindowController) KoinJavaComponent.get(WindowController.class)).getComponent()));
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_ICON, false);
        setModal(true);
        setResizable(false);
        setTitle(languageService.getString("about"));

        root = new JPanel();
        root.setBorder(new EmptyBorder(16, 16, 16, 16));
        root.setLayout(new BorderLayout());
        setContentPane(root);

        content = new JPanel();
        content.setBorder(new EmptyBorder(0, 0, 16, 0));
        content.setLayout(new MigLayout());
        root.add(content, BorderLayout.CENTER);

        logo = new FlatLabel();
        logo.setBorder(new EmptyBorder(0, 0, 0, 16));
        logo.setIcon(new ImageIcon(getOwner().getIconImages().get(0).getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        logo.setVerticalAlignment(JLabel.TOP);
        content.add(logo, "dock west");

        header = new FlatLabel();
        header.setStyleClass("h1");
        header.setText("Decompiler");
        content.add(header, "wrap");

        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        version = new FlatLabel();
        version.setText(String.format(languageService.getString("about.version"), properties.getProperty("version")));
        content.add(version, "wrap");

        copyright = new FlatLabel();
        copyright.setText(String.format("\u00a9 2022 - %s S\u014Dta", Year.now().getValue()));
        content.add(copyright, "wrap");

        vm = new JPanel();
        vm.setBorder(BorderFactory.createTitledBorder(languageService.getString("about.vm")));
        vm.setLayout(new MigLayout());
        content.add(vm, "wrap, gapy 16px");

        vmName = new FlatLabel();
        vmName.setText(ManagementFactory.getRuntimeMXBean().getVmName());
        vm.add(vmName, "wrap");

        vmVendor = new FlatLabel();
        vmVendor.setText(ManagementFactory.getRuntimeMXBean().getVmVendor());
        vm.add(vmVendor, "wrap");

        vmVersion = new FlatLabel();
        vmVersion.setText(ManagementFactory.getRuntimeMXBean().getVmVersion());
        vm.add(vmVersion, "wrap");

        controls = new JPanel();
        controls.add(Box.createHorizontalGlue());
        controls.setLayout(new BoxLayout(controls, BoxLayout.X_AXIS));
        root.add(controls, BorderLayout.SOUTH);

        github = new FlatButton();
        github.addActionListener(this::onGitHubAction);
        github.setFocusable(false);
        github.setText("GitHub");
        controls.add(github);

        controls.add(Box.createHorizontalStrut(8));

        ok = new FlatButton();
        ok.addActionListener(this::onOkAction);
        ok.setText(languageService.getString("about.ok"));
        controls.add(ok);
        getRootPane().setDefaultButton(ok);

        pack();
        setLocationRelativeTo(getOwner());
    }

    @SneakyThrows
    private void onGitHubAction(ActionEvent event) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
            Desktop.getDesktop().browse(new URI("https://github.com/sotasan/decompiler"));
    }

    private void onOkAction(ActionEvent event) {
        dispose();
    }

}
