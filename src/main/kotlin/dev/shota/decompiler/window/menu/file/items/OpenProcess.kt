package dev.shota.decompiler.window.menu.file.items

import com.sun.tools.attach.VirtualMachine
import dev.shota.decompiler.window.utils.translate
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.KeyStroke

class OpenProcess : JMenuItem("${translate("file.openProcess")}...", KeyEvent.VK_O), ActionListener {

    init {
        isEnabled = false
        accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx + KeyEvent.SHIFT_DOWN_MASK)
        addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent?) {
        for (vm in VirtualMachine.list())
            println(vm.displayName())
    }

}