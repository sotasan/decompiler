package moe.sota.decompiler.jvm.views

import com.formdev.flatlaf.extras.components.FlatLabel
import java.awt.Toolkit
import java.awt.event.KeyEvent
import javax.swing.JPanel
import moe.sota.decompiler.jvm.services.LanguageService
import net.miginfocom.swing.MigLayout

class StartView(languageService: LanguageService) : JPanel() {
    private val root: JPanel
    private val header: FlatLabel
    private val open: FlatLabel
    private val drag: FlatLabel

    init {
        layout = MigLayout("fill")

        root = JPanel().apply { layout = MigLayout("gapy 15") }
        add(root, "center")

        header =
            FlatLabel().apply {
                styleClass = "h1"
                text = languageService.getString("empty")
            }
        root.add(header, "wrap")

        open =
            FlatLabel().apply {
                val group = languageService.getString("file")
                val item = languageService.getString("file.openFile")
                val modifier =
                    KeyEvent.getModifiersExText(
                        Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()
                    )
                val key = KeyEvent.getKeyText(KeyEvent.VK_O)
                text = "$group > $item ($modifier + $key)"
            }
        root.add(open, "wrap")

        drag = FlatLabel().apply { text = languageService.getString("empty.drag") }
        root.add(drag, "wrap")
    }
}
