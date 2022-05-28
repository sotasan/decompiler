package net.pryoscode.decompiler.window.container

import javafx.collections.ListChangeListener
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.Tab
import javafx.scene.image.ImageView
import javafx.scene.input.ScrollEvent
import net.pryoscode.decompiler.window.sidebar.Entry
import net.pryoscode.decompiler.window.sidebar.Type
import org.fxmisc.flowless.ScaledVirtualized
import org.fxmisc.flowless.VirtualizedScrollPane
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory
import org.fxmisc.richtext.model.StyleSpans
import org.fxmisc.richtext.model.StyleSpansBuilder
import java.util.*
import java.util.regex.Pattern

class Code(val entry: Entry, private val code: String) : Tab() {

    private val keywords = arrayOf(
        "abstract", "assert", "boolean", "break", "byte",
        "case", "catch", "char", "class", "const",
        "continue", "default", "do", "double", "else",
        "enum", "extends", "final", "finally", "float",
        "for", "goto", "if", "implements", "import",
        "instanceof", "int", "interface", "long", "native",
        "new", "package", "private", "protected", "public",
        "return", "short", "static", "strictfp", "super",
        "switch", "synchronized", "this", "throw", "throws",
        "transient", "try", "void", "volatile", "while"
    )

    private val keyword     = "\\b(${keywords.joinToString("|")})\\b"
    private val paren       = "\\(|\\)"
    private val brace       = "\\{|\\}"
    private val bracket     = "\\[|\\]"
    private val semicolon   = "\\;"
    private val string      = "\"([^\"\\\\]|\\\\.)*\""
    private val comment     = "//[^\\n]*|/\\\\*(.|\\\\R)*?\\\\*/|/\\\\*[^\\\\v]*|^\\\\h*\\\\*([^\\\\v]*|/)"

    private val pattern = Pattern.compile(
        "(?<KEYWORD>$keyword)" +
        "|(?<PAREN>$paren)" +
        "|(?<BRACE>$brace)" +
        "|(?<BRACKET>$bracket)" +
        "|(?<SEMICOLON>$semicolon)" +
        "|(?<STRING>$string)" +
        "|(?<COMMENT>$comment)"
    )

    init {
        text = entry.name
        graphic = ImageView(entry.type.icon)

        val codeArea = CodeArea(code)
        codeArea.isEditable = false
        codeArea.paragraphGraphicFactory = LineNumberFactory.get(codeArea)
        if (entry.type == Type.CLASS) codeArea.setStyleSpans(0, highlighting())
        val scaled = ScaledVirtualized(codeArea)
        content = VirtualizedScrollPane(scaled)
        codeArea.addEventFilter(ScrollEvent.ANY) {
            if (it.isControlDown) {
                val scale = if (it.deltaY < 0) scaled.zoom.y * 0.9 else scaled.zoom.y / 0.9
                if (scale > 0.5 && scale < 5) {
                    scaled.zoom.x = scale
                    scaled.zoom.y = scale
                }
            }
        }

        val menu = ContextMenu()
        val close = MenuItem("Close")
        val closeOthers = MenuItem("Close Others")
        val closeAll = MenuItem("Close All")
        Container.tabs.addListener(ListChangeListener {
            val multiple = Container.tabs.size == 1
            closeOthers.isDisable = multiple
            closeAll.isDisable = multiple
        })
        close.setOnAction { Container.tabs.remove(this) }
        closeOthers.setOnAction {
            val tabs = Container.tabs.iterator()
            while (tabs.hasNext()) {
                if (!tabs.next().equals(this))
                    tabs.remove()
            }
        }
        closeAll.setOnAction { Container.tabs.clear() }
        menu.items.addAll(close, closeOthers, closeAll)
        contextMenu = menu

        Container.tabs.add(this)
        Container.selectionModel.select(this)
    }

    private fun highlighting(): StyleSpans<Collection<String>>? {
        val matcher = pattern.matcher(code)
        val builder = StyleSpansBuilder<Collection<String>>()
        var end = 0
        while (matcher.find()) {
            val style = (
                if (matcher.group("KEYWORD") != null) "keyword"
                else if (matcher.group("PAREN") != null) "paren"
                else if (matcher.group("BRACE") != null) "brace"
                else if (matcher.group("BRACKET") != null) "bracket"
                else if (matcher.group("SEMICOLON") != null) "semicolon"
                else if (matcher.group("STRING") != null) "string"
                else if (matcher.group("COMMENT") != null) "comment"
                else null
            )!!
            builder.add(Collections.emptyList(), matcher.start() - end)
            builder.add(Collections.singleton(style), matcher.end() - matcher.start())
            end = matcher.end()
        }
        builder.add(Collections.emptyList(), code.length - end)
        return builder.create()
    }

}