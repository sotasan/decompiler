package moe.sota.decompiler.views

import java.awt.BorderLayout
import java.awt.Cursor
import java.awt.Dimension
import java.awt.Image
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.MouseWheelEvent
import java.awt.event.MouseWheelListener
import javax.swing.BorderFactory
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import moe.sota.decompiler.controllers.TabController
import moe.sota.decompiler.models.FileModel
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
import org.fife.ui.rsyntaxtextarea.Theme
import org.fife.ui.rtextarea.RTextScrollPane

class TabView(private val fileModel: FileModel) : JPanel(), MouseWheelListener {
    val textArea: RSyntaxTextArea
    val scrollPane: RTextScrollPane
    var tabController: TabController? = null

    init {
        layout = BorderLayout()

        val theme = Theme.load(javaClass.classLoader.getResourceAsStream("themes/RSyntaxTheme.xml"))

        textArea = RSyntaxTextArea()
        theme.apply(textArea)
        textArea.apply {
            addMouseWheelListener(this@TabView)
            isBracketMatchingEnabled = false
            cursor = Cursor(Cursor.TEXT_CURSOR)
            dropTarget = null
            isEditable = false
            highlightCurrentLine = false
        }

        scrollPane = RTextScrollPane(textArea)
        theme.apply(textArea)
        scrollPane.border = BorderFactory.createEmptyBorder()
        add(scrollPane)

        setFontSize(font.size + 2f)
    }

    // TODO: global font size
    override fun mouseWheelMoved(event: MouseWheelEvent) {
        if (event.isControlDown || event.isMetaDown)
            setFontSize((textArea.font.size - event.wheelRotation).coerceIn(10, 50).toFloat())
        else scrollPane.mouseWheelListeners.forEach { it.mouseWheelMoved(event) }
    }

    private fun setFontSize(size: Float) {
        val font = textArea.font.deriveFont(size)
        textArea.font = font
        scrollPane.gutter.lineNumberFont = font
    }

    fun showImage() {
        layout = BorderLayout()
        removeAll()

        val imageScrollPane = JScrollPane()
        val originalIcon = ImageIcon(fileModel.bytes)
        val imageLabel =
            JLabel().apply {
                icon = originalIcon
                horizontalAlignment = JLabel.CENTER
                verticalAlignment = JLabel.CENTER
            }

        imageScrollPane.setViewportView(imageLabel)
        imageScrollPane.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        imageScrollPane.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_NEVER

        imageScrollPane.addComponentListener(
            object : ComponentAdapter() {
                override fun componentResized(event: ComponentEvent) {
                    var height = this@TabView.height
                    var width =
                        (originalIcon.iconWidth * (height.toDouble() / originalIcon.iconHeight))
                            .toInt()

                    if (width > this@TabView.width) {
                        width = this@TabView.width
                        height =
                            (originalIcon.iconHeight * (width.toDouble() / originalIcon.iconWidth))
                                .toInt()
                    }

                    // Verify if the image is bigger than the scroll pane
                    if (originalIcon.iconWidth > width || originalIcon.iconHeight > height) {
                        imageLabel.icon =
                            ImageIcon(
                                originalIcon.image.getScaledInstance(
                                    width,
                                    height,
                                    Image.SCALE_SMOOTH,
                                )
                            )
                        imageLabel.preferredSize = Dimension(width, height)
                    } else {
                        imageLabel.icon = originalIcon
                        imageLabel.preferredSize =
                            Dimension(originalIcon.iconWidth, originalIcon.iconHeight)
                    }
                }
            }
        )

        add(imageScrollPane, BorderLayout.CENTER)
        revalidate()
        repaint()
    }
}
