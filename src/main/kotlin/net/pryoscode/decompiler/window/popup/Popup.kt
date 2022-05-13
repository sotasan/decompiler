package net.pryoscode.decompiler.window.popup

import javafx.embed.swing.JFXPanel
import javafx.scene.Parent
import javafx.scene.Scene
import net.pryoscode.decompiler.window.Window
import net.pryoscode.decompiler.window.utils.style
import java.awt.event.KeyEvent
import javax.swing.ImageIcon
import javax.swing.JComponent
import javax.swing.JDialog
import javax.swing.JRootPane
import javax.swing.KeyStroke
import javax.swing.SwingUtilities

open class Popup(title: String) : JDialog(Window, title, true) {

    fun run(root: Parent) {
        val panel = JFXPanel()
        root.stylesheets.add(style("base.less"))
        panel.scene = Scene(root)

        val jroot = JRootPane()
        jroot.contentPane = panel
        jroot.registerKeyboardAction({ dispose() }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW)
        add(jroot)

        owner.setIconImage(ImageIcon(javaClass.classLoader.getResourceAsStream("icons/logo.png")?.readAllBytes()).image)
        isResizable = false
        SwingUtilities.invokeLater {
            pack()
            setLocationRelativeTo(parent)
            isVisible = true
        }
    }

}