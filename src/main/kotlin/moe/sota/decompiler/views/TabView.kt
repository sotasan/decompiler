package moe.sota.decompiler.views

import moe.sota.decompiler.controllers.TabController
import moe.sota.decompiler.models.FileModel
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
import org.fife.ui.rsyntaxtextarea.Theme
import org.fife.ui.rtextarea.RTextScrollPane
import java.awt.BorderLayout
import java.awt.Cursor
import java.awt.Dimension
import java.awt.Image
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.MouseWheelEvent
import java.awt.event.MouseWheelListener
import javax.swing.*
import kotlin.math.max
import kotlin.math.min

class TabView(
    val fileModel: FileModel
) : JPanel(), MouseWheelListener {

    val textArea: RSyntaxTextArea
    val scrollPane: RTextScrollPane
    var tabController: TabController? = null

    init {
        setLayout(BorderLayout())

        val theme = Theme.load(javaClass.getClassLoader().getResourceAsStream("themes/RSyntaxTheme.xml"))

        textArea = RSyntaxTextArea()
        theme.apply(textArea)
        textArea.addMouseWheelListener(this)
        textArea.isBracketMatchingEnabled = false
        textArea.setCursor(Cursor(Cursor.TEXT_CURSOR))
        textArea.setDropTarget(null)
        textArea.isEditable = false
        textArea.highlightCurrentLine = false

        scrollPane = RTextScrollPane(textArea)
        theme.apply(textArea)
        scrollPane.setBorder(BorderFactory.createEmptyBorder())
        add(scrollPane)

        setFontSize((getFont().getSize() + 2).toFloat())
    }

    // TODO: global font size
    override fun mouseWheelMoved(event: MouseWheelEvent) {
        if (event.isControlDown || event.isMetaDown) setFontSize(
            min(
                50,
                max(10, textArea.getFont().getSize() - event.getWheelRotation())
            ).toFloat()
        )
        else for (listener in scrollPane.mouseWheelListeners) listener.mouseWheelMoved(event)
    }

    private fun setFontSize(size: Float) {
        val font = textArea.getFont().deriveFont(size)
        textArea.setFont(font)
        scrollPane.gutter.setLineNumberFont(font)
    }

    fun setScrollPane(imageScrollPane: JScrollPane) {
        setLayout(BorderLayout())
        removeAll()

        val imageLabel = JLabel()
        val originalIcon = ImageIcon(fileModel.bytes)
        imageLabel.setIcon(originalIcon)
        imageLabel.setHorizontalAlignment(JLabel.CENTER)
        imageLabel.setVerticalAlignment(JLabel.CENTER)

        imageScrollPane.setViewportView(imageLabel)
        imageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)
        imageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER)

        imageScrollPane.addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent?) {
                var height = getHeight()
                var width = (originalIcon.iconWidth * (height.toDouble() / originalIcon.iconHeight)).toInt()

                if (width > getWidth()) {
                    width = getWidth()
                    height = (originalIcon.iconHeight * (width.toDouble() / originalIcon.iconWidth)).toInt()
                }

                // Verify if the image is bigger than the scroll pane
                if (originalIcon.iconWidth > width || originalIcon.iconHeight > height) {
                    val scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)
                    imageLabel.setIcon(ImageIcon(scaledImage))
                    imageLabel.preferredSize = Dimension(width, height)
                } else {
                    imageLabel.setIcon(originalIcon)
                    imageLabel.preferredSize = Dimension(originalIcon.iconWidth, originalIcon.iconHeight)
                }
            }
        })

        add(imageScrollPane, BorderLayout.CENTER)
        revalidate()
        repaint()
    }

}
