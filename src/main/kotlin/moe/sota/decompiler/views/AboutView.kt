package moe.sota.decompiler.views

import com.formdev.flatlaf.FlatClientProperties
import com.formdev.flatlaf.extras.components.FlatButton
import com.formdev.flatlaf.extras.components.FlatLabel
import moe.sota.decompiler.services.LanguageService
import net.miginfocom.swing.MigLayout
import java.awt.BorderLayout
import java.awt.Desktop
import java.awt.Image
import java.awt.event.ActionEvent
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
        isModal = true
        isResizable = false
        title = languageService.getString("about")
        getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_ICON, false)

        root = JPanel().apply {
            border = EmptyBorder(16, 16, 16, 16)
            layout = BorderLayout()
        }
        contentPane = root

        content = JPanel().apply {
            border = EmptyBorder(0, 0, 16, 0)
            layout = MigLayout()
        }
        root.add(content, BorderLayout.CENTER)

        logo = FlatLabel().apply {
            border = EmptyBorder(0, 0, 0, 16)
            icon = ImageIcon(owner.getIconImages()[0].getScaledInstance(64, 64, Image.SCALE_SMOOTH))
            verticalAlignment = JLabel.TOP
        }
        content.add(logo, "dock west")

        header = FlatLabel().apply {
            styleClass = "h1"
            text = "Decompiler"
        }
        content.add(header, "wrap")

        version = FlatLabel().apply {
            val properties = Properties()
            properties.load(javaClass.classLoader.getResourceAsStream("application.properties"))
            text = String.format(languageService.getString("about.version"), properties.getProperty("version"))
        }
        content.add(version, "wrap")

        copyright = FlatLabel().apply {
            text = String.format("\u00a9 2022 - %s S\u014Dta", Year.now().value)
        }
        content.add(copyright, "wrap")

        vm = JPanel().apply {
            border = BorderFactory.createTitledBorder(languageService.getString("about.vm"))
            layout = MigLayout()
        }
        content.add(vm, "wrap, gapy 16px")

        vmName = FlatLabel().apply {
            text = ManagementFactory.getRuntimeMXBean().vmName
        }
        vm.add(vmName, "wrap")

        vmVendor = FlatLabel().apply {
            text = ManagementFactory.getRuntimeMXBean().vmVendor
        }
        vm.add(vmVendor, "wrap")

        vmVersion = FlatLabel().apply {
            text = ManagementFactory.getRuntimeMXBean().vmVersion
        }
        vm.add(vmVersion, "wrap")

        controls = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            add(Box.createHorizontalGlue())
        }
        root.add(controls, BorderLayout.SOUTH)

        github = FlatButton().apply {
            isFocusable = false
            text = "GitHub"
            addActionListener(::onGitHubAction)
        }
        controls.add(github)

        controls.add(Box.createHorizontalStrut(8))

        ok = FlatButton().apply {
            text = languageService.getString("about.ok")
            addActionListener(::onOkAction)
        }
        controls.add(ok)
        getRootPane().setDefaultButton(ok)

        pack()
        setLocationRelativeTo(owner)
    }

    private fun onGitHubAction(event: ActionEvent?) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
            Desktop.getDesktop().browse(URI("https://github.com/sotasan/decompiler"))
    }

    private fun onOkAction(event: ActionEvent?) {
        dispose()
    }

}
