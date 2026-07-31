package moe.sota.decompiler.views

import com.formdev.flatlaf.extras.components.FlatLabel
import java.awt.Toolkit
import java.awt.event.KeyEvent
import javax.swing.JPanel
import moe.sota.decompiler.services.LanguageService
import net.miginfocom.swing.MigLayout

class StartView(languageService: LanguageService) : JPanel() {
    init {
        layout = MigLayout("fill")

        val root = JPanel().apply { layout = MigLayout("gapy 15") }
        add(root, "center")

        val header =
            FlatLabel().apply {
                styleClass = "h1"
                text = languageService.getString("empty")
            }
        root.add(header, "wrap")

        val open =
            FlatLabel().apply {
                val group = languageService.getString("file")
                val item = languageService.getString("file.openFile")
                val modifier =
                    KeyEvent.getModifiersExText(Toolkit.getDefaultToolkit().menuShortcutKeyMaskEx)
                val key = KeyEvent.getKeyText(KeyEvent.VK_O)
                text = "$group > $item ($modifier + $key)"
            }
        root.add(open, "wrap")

        val drag = FlatLabel().apply { text = languageService.getString("empty.drag") }
        root.add(drag, "wrap")
    }
}
