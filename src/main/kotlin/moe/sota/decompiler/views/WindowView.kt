package moe.sota.decompiler.views

import com.formdev.flatlaf.extras.components.FlatSplitPane
import com.formdev.flatlaf.util.SystemInfo
import moe.sota.decompiler.menus.MenuBar
import moe.sota.decompiler.services.LoaderService
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
import java.util.*
import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.system.exitProcess

class WindowView(
    menuBar: MenuBar,
    startView: StartView,
    tabsView: TabsView,
    treeView: TreeView
) : JFrame() {

    val splitPane: FlatSplitPane
    var macos: JPanel? = null

    init {
        addComponentListener(WindowComponentAdapter(this))
        contentPane = startView
        setDefaultCloseOperation(DISPOSE_ON_CLOSE)
        setDropTarget(WindowDropTarget())
        jMenuBar = menuBar
        minimumSize = Dimension(500, 300)
        setPreferredSize(Dimension(1000, 600))
        setTitle("Decompiler")

        val logo = if (SystemInfo.isMacOS) "logo/logo-macos.png" else "logo/logo.png"
        val image = Toolkit.getDefaultToolkit().createImage(javaClass.getClassLoader().getResource(logo))
        if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.ICON_IMAGE))
            Taskbar.getTaskbar().setIconImage(image)
        iconImage = image

        splitPane = FlatSplitPane()
        splitPane.setDividerLocation(225)
        splitPane.setRightComponent(tabsView)

        val panel = JPanel()
        panel.setLayout(BoxLayout(panel, BoxLayout.Y_AXIS))
        panel.minimumSize = Dimension(100, 0)
        splitPane.setLeftComponent(panel)

        if (SystemInfo.isMacFullWindowContentSupported) {
            getRootPane().putClientProperty("apple.awt.fullWindowContent", true)
            getRootPane().putClientProperty("apple.awt.transparentTitleBar", true)
            getRootPane().putClientProperty("apple.awt.windowTitleVisible", false)

            macos = JPanel().apply {
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

private class WindowComponentAdapter(
    private val windowView: WindowView
) : ComponentAdapter() {

    override fun componentResized(event: ComponentEvent?) {
        if (SystemInfo.isMacOS && windowView.macos != null)
            windowView.isVisible =
                windowView.height < GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds.height
    }

}

private class WindowDropTarget : DropTarget() {

    override fun dragOver(event: DropTargetDragEvent) {
        event.acceptDrag(DnDConstants.ACTION_MOVE)
    }

    override fun drop(event: DropTargetDropEvent) {
        event.acceptDrop(DnDConstants.ACTION_MOVE)

        if (event.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            val files = event.transferable.getTransferData(DataFlavor.javaFileListFlavor) as MutableList<*>

            if (!files.isEmpty()) {
                val file = files[0] as File
                val name = file.getName().lowercase(Locale.getDefault())

                if (name.endsWith(".jar") || name.endsWith(".war") || name.endsWith(".zip")) {
                    LoaderService.loadAsync(file)
                    event.dropComplete(true)
                }
            }
        }
    }

}
