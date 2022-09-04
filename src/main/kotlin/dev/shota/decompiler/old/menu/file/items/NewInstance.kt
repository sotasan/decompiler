package dev.shota.decompiler.old.menu.file.items

import dev.shota.decompiler.Main
import dev.shota.decompiler.window.menu.MenuItem
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.lang.management.ManagementFactory

class NewInstance : MenuItem("file.newInstance", KeyEvent.VK_N) {

    override fun actionPerformed(e: ActionEvent?) {
        val java = ProcessHandle.current().info().command().get()
        val classPath = ManagementFactory.getRuntimeMXBean().classPath
        val main = Main::class.java.canonicalName
        ProcessBuilder(java, "-cp", classPath, main).start()
    }

}