package dev.shota.decompiler.window.menu.file.items

import dev.shota.decompiler.Main
import dev.shota.decompiler.window.utils.language
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.lang.management.ManagementFactory
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class NewWindow : JMenuItem(language("file.newWindow"), KeyEvent.VK_N), ActionListener {

    init {
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
        addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent?) {
        val java = ProcessHandle.current().info().command().get()
        val classPath = ManagementFactory.getRuntimeMXBean().classPath
        val main = Main::class.java.canonicalName
        ProcessBuilder(java, "-cp", classPath, main).start()
    }

}