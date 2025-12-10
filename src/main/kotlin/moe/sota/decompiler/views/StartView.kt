package moe.sota.decompiler.views

import com.formdev.flatlaf.extras.components.FlatLabel
import moe.sota.decompiler.services.LanguageService
import net.miginfocom.swing.MigLayout
import java.awt.Toolkit
import java.awt.event.KeyEvent
import javax.swing.JPanel

class StartView(
    languageService: LanguageService
) : JPanel() {

    private val root: JPanel
    private val header: FlatLabel
    private val open: FlatLabel
    private val drag: FlatLabel

    init {
        layout = MigLayout("fill")

        root = JPanel()
        root.layout = MigLayout("gapy 15")
        add(root, "center")

        header = FlatLabel()
        header.styleClass = "h1"
        header.text = languageService.getString("empty")
        root.add(header, "wrap")

        val group = languageService.getString("file")
        val item = languageService.getString("file.openFile")
        val modifier = KeyEvent.getModifiersExText(Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx())
        val key = KeyEvent.getKeyText(KeyEvent.VK_O)
        open = FlatLabel()
        open.text = "$group > $item ($modifier + $key)"
        root.add(open, "wrap")

        drag = FlatLabel()
        drag.text = languageService.getString("empty.drag")
        root.add(drag, "wrap")
    }

}
