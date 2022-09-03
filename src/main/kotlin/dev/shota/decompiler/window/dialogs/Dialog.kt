package dev.shota.decompiler.window.dialogs

import javafx.embed.swing.JFXPanel
import javafx.scene.Parent
import javafx.scene.Scene
import dev.shota.decompiler.window.WindowOld
import dev.shota.decompiler.window.menu.view.items.Language
import dev.shota.decompiler.window.utils.assets
import dev.shota.decompiler.window.utils.styles
import java.awt.event.KeyEvent
import javax.swing.ImageIcon
import javax.swing.JComponent
import javax.swing.JDialog
import javax.swing.JRootPane
import javax.swing.KeyStroke
import javax.swing.SwingUtilities

open class Dialog(title: String) : JDialog(WindowOld, Language.get(title).value, true) {

    fun run(root: Parent) {
        val panel = JFXPanel()
        root.stylesheets.add(styles("main"))
        panel.scene = Scene(root)

        val jroot = JRootPane()
        jroot.contentPane = panel
        jroot.registerKeyboardAction({ dispose() }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW)
        add(jroot)

        setIconImage(ImageIcon(assets("logo/logo.png")).image)
        isResizable = false
        SwingUtilities.invokeLater {
            pack()
            setLocationRelativeTo(parent)
            isVisible = true
        }
    }

}