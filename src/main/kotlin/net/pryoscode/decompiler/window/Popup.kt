package net.pryoscode.decompiler.window

import javafx.embed.swing.JFXPanel
import javafx.scene.Parent
import javafx.scene.Scene
import net.pryoscode.decompiler.window.windows.Window
import java.awt.event.KeyEvent
import javax.swing.ImageIcon
import javax.swing.JComponent
import javax.swing.JDialog
import javax.swing.JRootPane
import javax.swing.KeyStroke

open class Popup(title: String, private val w: Int, private val h: Int) : JDialog(Window, title, true) {

    fun run(root: Parent) {
        val panel = JFXPanel()
        root.stylesheets.add(style("base.less"))
        panel.scene = Scene(root, w.toDouble(), h.toDouble())

        val jroot = JRootPane()
        jroot.contentPane = panel
        jroot.registerKeyboardAction({ dispose() }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW)
        add(jroot)

        setSize(w, h)
        setLocationRelativeTo(parent)
        owner.setIconImage(ImageIcon(javaClass.classLoader.getResourceAsStream("icons/logo.png")?.readAllBytes()).image)
        isResizable = false
        isVisible = true
    }

}