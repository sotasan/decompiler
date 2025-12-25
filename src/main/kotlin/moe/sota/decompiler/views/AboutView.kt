package moe.sota.decompiler.views

import com.formdev.flatlaf.FlatClientProperties
import com.formdev.flatlaf.extras.components.FlatButton
import com.formdev.flatlaf.extras.components.FlatLabel
import lombok.SneakyThrows
import moe.sota.decompiler.services.LanguageService
import net.miginfocom.swing.MigLayout
import java.awt.BorderLayout
import java.awt.Desktop
import java.awt.Image
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.lang.management.ManagementFactory
import java.net.URI
import java.time.Year
import java.util.*
import javax.swing.*
import javax.swing.border.EmptyBorder

class AboutView(
    languageService: LanguageService,
    windowView: WindowView
) : JDialog(windowView) {

    val root: JPanel
    val content: JPanel
    val logo: FlatLabel
    val header: FlatLabel
    val copyright: FlatLabel
    val version: FlatLabel
    val vm: JPanel
    val vmName: FlatLabel
    val vmVendor: FlatLabel
    val vmVersion: FlatLabel
    val controls: JPanel
    val github: FlatButton
    val ok: FlatButton

    init {
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_ICON, false)
        setModal(true)
        setResizable(false)
        setTitle(languageService.getString("about"))

        root = JPanel()
        root.setBorder(EmptyBorder(16, 16, 16, 16))
        root.setLayout(BorderLayout())
        contentPane = root

        content = JPanel()
        content.setBorder(EmptyBorder(0, 0, 16, 0))
        content.setLayout(MigLayout())
        root.add(content, BorderLayout.CENTER)

        logo = FlatLabel()
        logo.setBorder(EmptyBorder(0, 0, 0, 16))
        logo.setIcon(ImageIcon(owner.getIconImages().get(0).getScaledInstance(64, 64, Image.SCALE_SMOOTH)))
        logo.setVerticalAlignment(JLabel.TOP)
        content.add(logo, "dock west")

        header = FlatLabel()
        header.styleClass = "h1"
        header.setText("Decompiler")
        content.add(header, "wrap")

        val properties = Properties()
        properties.load(javaClass.getClassLoader().getResourceAsStream("application.properties"))
        version = FlatLabel()
        version.setText(String.format(languageService.getString("about.version"), properties.getProperty("version")))
        content.add(version, "wrap")

        copyright = FlatLabel()
        copyright.setText(String.format("\u00a9 2022 - %s S\u014Dta", Year.now().value))
        content.add(copyright, "wrap")

        vm = JPanel()
        vm.setBorder(BorderFactory.createTitledBorder(languageService.getString("about.vm")))
        vm.setLayout(MigLayout())
        content.add(vm, "wrap, gapy 16px")

        vmName = FlatLabel()
        vmName.setText(ManagementFactory.getRuntimeMXBean().vmName)
        vm.add(vmName, "wrap")

        vmVendor = FlatLabel()
        vmVendor.setText(ManagementFactory.getRuntimeMXBean().vmVendor)
        vm.add(vmVendor, "wrap")

        vmVersion = FlatLabel()
        vmVersion.setText(ManagementFactory.getRuntimeMXBean().vmVersion)
        vm.add(vmVersion, "wrap")

        controls = JPanel()
        controls.add(Box.createHorizontalGlue())
        controls.setLayout(BoxLayout(controls, BoxLayout.X_AXIS))
        root.add(controls, BorderLayout.SOUTH)

        github = FlatButton()
        github.addActionListener(ActionListener { event: ActionEvent? -> this.onGitHubAction(event) })
        github.setFocusable(false)
        github.setText("GitHub")
        controls.add(github)

        controls.add(Box.createHorizontalStrut(8))

        ok = FlatButton()
        ok.addActionListener(ActionListener { event: ActionEvent? -> this.onOkAction(event) })
        ok.setText(languageService.getString("about.ok"))
        controls.add(ok)
        getRootPane().setDefaultButton(ok)

        pack()
        setLocationRelativeTo(owner)
    }

    @SneakyThrows
    private fun onGitHubAction(event: ActionEvent?) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop()
                .isSupported(Desktop.Action.BROWSE)
        ) Desktop.getDesktop().browse(
            URI("https://github.com/sotasan/decompiler")
        )
    }

    private fun onOkAction(event: ActionEvent?) {
        dispose()
    }

}
