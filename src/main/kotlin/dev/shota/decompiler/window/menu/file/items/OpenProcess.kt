package dev.shota.decompiler.window.menu.file.items

import com.sun.tools.attach.VirtualMachine
import dev.shota.decompiler.window.menu.MenuItem
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

class OpenProcess : MenuItem("file.openProcess", KeyEvent.VK_O, KeyEvent.SHIFT_DOWN_MASK) {

    init {
        isEnabled = false
    }

    override fun actionPerformed(e: ActionEvent?) {
        for (vm in VirtualMachine.list())
            println(vm.displayName())
    }

}