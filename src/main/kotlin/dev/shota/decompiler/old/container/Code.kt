package dev.shota.decompiler.old.container

import dev.shota.decompiler.jvm.Decompiler
import dev.shota.decompiler.old.menu.view.items.Language
import dev.shota.decompiler.old.sidebar.Type
import javafx.beans.property.SimpleDoubleProperty
import javafx.collections.ListChangeListener
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.Tab
import javafx.scene.image.ImageView
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.BorderPane
import org.fxmisc.flowless.ScaledVirtualized
import org.fxmisc.flowless.VirtualizedScrollPane
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory
import org.fxmisc.richtext.model.StyleSpans
import org.fxmisc.richtext.model.StyleSpansBuilder
import java.util.*
import java.util.regex.Pattern

class Code(name: String, val data: ByteArray, val clazz: Boolean) : Tab() {

    companion object {

        private val zoom = SimpleDoubleProperty(1.0)

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

        private val keyword = "\\b(${keywords.joinToString("|")})\\b"
        private const val paren = "\\(|\\)"
        private const val brace = "\\{|\\}"
        private const val bracket = "\\[|\\]"
        private const val semicolon = "\\;"
        private const val string = "\"([^\"\\\\]|\\\\.)*\""
        private const val comment = "//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/"

        private val pattern = Pattern.compile(
            "(?<KEYWORD>$keyword)" +
            "|(?<PAREN>$paren)" +
            "|(?<BRACE>$brace)" +
            "|(?<BRACKET>$bracket)" +
            "|(?<SEMICOLON>$semicolon)" +
            "|(?<STRING>$string)" +
            "|(?<COMMENT>$comment)"
        )

        fun changeZoom(scale: Double) {
            setZoom(zoom.get() + scale)
        }

        fun setZoom(scale: Double) {
            if (scale in 0.5..5.0)
                zoom.set(scale)
        }

    }

    val codeArea = CodeArea()
    var type = CodeType.JAVA

    init {
        val code = if (clazz) Decompiler(data).code else String(data)

        text = name
        graphic = ImageView(if (clazz) Type.CLASS.icon else Type.TEXT.icon)
        val root = BorderPane()
        content = root

        codeArea.isEditable = false
        codeArea.paragraphGraphicFactory = LineNumberFactory.get(codeArea)
        if (clazz) {
            codeArea.textProperty().addListener { _, _, _ ->
                codeArea.setStyleSpans(0, highlight())
            }
        }
        codeArea.replaceText(code)
        codeArea.moveTo(0)
        codeArea.requestFollowCaret()

        val scaled = ScaledVirtualized(codeArea)
        scaled.zoom.xProperty().bind(zoom)
        scaled.zoom.yProperty().bind(zoom)
        root.center = VirtualizedScrollPane(scaled)
        codeArea.addEventFilter(ScrollEvent.ANY) {
            if (it.isShortcutDown) {
                setZoom(if (it.deltaY < 0) scaled.zoom.y * 0.9 else scaled.zoom.y / 0.9)
                it.consume()
            }
        }

        contextMenu = ContextMenu()
        val close = MenuItem()
        val closeOthers = MenuItem()
        val closeAll = MenuItem()
        close.textProperty().bind(Language.get("tab.close"))
        closeOthers.textProperty().bind(Language.get("tab.closeOthers"))
        closeAll.textProperty().bind(Language.get("tab.closeAll"))
        Container.tabs.addListener(ListChangeListener { closeOthers.isDisable = Container.tabs.size == 1 })
        close.setOnAction { Container.tabs.remove(this) }
        closeOthers.setOnAction {
            val tabs = Container.tabs.iterator()
            while (tabs.hasNext()) {
                if (!tabs.next().equals(this))
                    tabs.remove()
            }
        }
        closeAll.setOnAction { Container.tabs.clear() }
        contextMenu.items.addAll(close, closeOthers, closeAll)
    }

    private fun highlight(): StyleSpans<Collection<String>> {
        val matcher = pattern.matcher(codeArea.text)
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
        builder.add(Collections.emptyList(), codeArea.text.length - end)
        return builder.create()
    }

}