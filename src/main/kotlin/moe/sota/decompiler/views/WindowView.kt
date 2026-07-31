package moe.sota.decompiler.views

import com.formdev.flatlaf.extras.components.FlatSplitPane
import com.formdev.flatlaf.util.SystemInfo
import java.awt.Dimension
import java.awt.GraphicsEnvironment
import java.awt.Taskbar
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTarget
import java.awt.dnd.DropTargetDragEvent
import java.awt.dnd.DropTargetDropEvent
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.io.File
import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.system.exitProcess
import moe.sota.decompiler.menus.MenuBar
import moe.sota.decompiler.services.LoaderService

class WindowView(menuBar: MenuBar, startView: StartView, tabsView: TabsView, treeView: TreeView) :
    JFrame() {
    val splitPane: FlatSplitPane
    internal var macos: JPanel? = null

    init {
        contentPane = startView
        defaultCloseOperation = DISPOSE_ON_CLOSE
        dropTarget = WindowDropTarget()
        jMenuBar = menuBar
        minimumSize = Dimension(500, 300)
        preferredSize = Dimension(1000, 600)
        title = "Decompiler"
        addComponentListener(WindowComponentAdapter(this))

        val logo = if (SystemInfo.isMacOS) "logo/logo-macos.png" else "logo/logo.png"
        val image = Toolkit.getDefaultToolkit().createImage(javaClass.classLoader.getResource(logo))
        if (
            Taskbar.isTaskbarSupported() &&
                Taskbar.getTaskbar().isSupported(Taskbar.Feature.ICON_IMAGE)
        )
            Taskbar.getTaskbar().iconImage = image
        iconImage = image

        splitPane =
            FlatSplitPane().apply {
                dividerLocation = 225
                rightComponent = tabsView
            }

        val panel =
            JPanel().apply {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)
                minimumSize = Dimension(100, 0)
            }
        splitPane.leftComponent = panel

        if (SystemInfo.isMacFullWindowContentSupported) {
            rootPane.putClientProperty("apple.awt.fullWindowContent", true)
            rootPane.putClientProperty("apple.awt.transparentTitleBar", true)
            rootPane.putClientProperty("apple.awt.windowTitleVisible", false)

            macos =
                JPanel().apply {
                    val dimension = Dimension(0, 30)
                    minimumSize = dimension
                    preferredSize = dimension
                }
            panel.add(macos)
        }

        panel.add(treeView)

        pack()
        setLocationRelativeTo(null)
    }

    override fun dispose() {
        super.dispose()
        exitProcess(0)
    }
}

private class WindowComponentAdapter(private val windowView: WindowView) : ComponentAdapter() {
    override fun componentResized(event: ComponentEvent) {
        if (SystemInfo.isMacOS && windowView.macos != null)
            windowView.isVisible =
                windowView.height <
                    GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds.height
    }
}

private class WindowDropTarget : DropTarget() {
    override fun dragOver(event: DropTargetDragEvent) {
        event.acceptDrag(DnDConstants.ACTION_MOVE)
    }

    override fun drop(event: DropTargetDropEvent) {
        event.acceptDrop(DnDConstants.ACTION_MOVE)

        if (!event.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) return

        val files = event.transferable.getTransferData(DataFlavor.javaFileListFlavor) as List<*>
        val file = files.firstOrNull() as File? ?: return

        if (listOf(".jar", ".war", ".zip").any { file.name.endsWith(it, ignoreCase = true) }) {
            LoaderService.load(file)
            event.dropComplete(true)
        }
    }
}
