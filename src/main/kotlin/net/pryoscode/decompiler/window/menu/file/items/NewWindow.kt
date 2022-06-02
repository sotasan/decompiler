package net.pryoscode.decompiler.window.menu.file.items

import net.pryoscode.decompiler.Main
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.lang.management.ManagementFactory
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class NewWindow : JMenuItem("New Window", KeyEvent.VK_N), ActionListener {

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK)
        addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent?) {
        val java = ProcessHandle.current().info().command().get()
        val classPath = ManagementFactory.getRuntimeMXBean().classPath
        val main = Main::class.java.canonicalName
        ProcessBuilder(java, "-cp", classPath, main).start()
    }

}