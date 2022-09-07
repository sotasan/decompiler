package dev.shota.decompiler.window

import com.formdev.flatlaf.util.SystemInfo
import dev.shota.decompiler.loader.FileLoader
import dev.shota.decompiler.window.explorer.Explorer
import dev.shota.decompiler.window.menu.MenuBar
import dev.shota.decompiler.window.utils.Styles
import dev.shota.decompiler.window.viewer.Viewer
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.control.SplitPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import java.awt.Dimension
import java.awt.Taskbar
import java.awt.datatransfer.DataFlavor
import java.awt.dnd.*
import java.io.File
import java.util.*
import java.util.stream.Collectors
import javax.swing.ImageIcon
import javax.swing.JFrame
import kotlin.system.exitProcess

object Window : JFrame(), DropTargetListener {

    init {
        val sidebar = BorderPane(Explorer)
        val root = SplitPane(sidebar, Viewer)
        SplitPane.setResizableWithParent(sidebar, false)
        root.stylesheets.add(Styles.get("main"))
        root.setDividerPositions(Explorer.minWidth / (Explorer.minWidth + Viewer.minWidth), Viewer.minWidth / (Explorer.minWidth + Viewer.minWidth))

        if (SystemInfo.isMacOS && SystemInfo.isMacFullWindowContentSupported) {
            rootPane.putClientProperty("apple.awt.fullWindowContent", true)
            rootPane.putClientProperty("apple.awt.transparentTitleBar", true)
            rootPane.putClientProperty("apple.awt.windowTitleVisible", false)
            rootPane.putClientProperty("apple.awt.fullscreenable", true)
            Pane().run {
                prefHeight = 25.0
                sidebar.top = this
            }
        }

        val logo = ImageIcon(Objects.requireNonNull(javaClass.classLoader.getResourceAsStream("logo/logo.png")).readAllBytes()).image
        if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.ICON_IMAGE)) Taskbar.getTaskbar().iconImage = logo
        iconImage = logo

        dropTarget = DropTarget().also {
            it.addDropTargetListener(this)
        }

        JFXPanel().let {
            it.scene = Scene(root, 894.0, 528.0)
            it.dropTarget.isActive = false
            add(it)
        }

        title = "Decompiler"
        defaultCloseOperation = DISPOSE_ON_CLOSE
        jMenuBar = MenuBar()
        minimumSize = Dimension((Explorer.minWidth + Viewer.minWidth).toInt(), ((Explorer.minWidth + Viewer.minWidth) / 2).toInt())
        pack()
        setLocationRelativeTo(null)
    }

    override fun dispose() {
        super.dispose()
        exitProcess(0)
    }

    override fun dragEnter(event: DropTargetDragEvent?) {
        event?.acceptDrag(DnDConstants.ACTION_COPY)
    }

    override fun dragOver(event: DropTargetDragEvent?) {
        event?.acceptDrag(DnDConstants.ACTION_COPY)
    }

    override fun drop(event: DropTargetDropEvent?) {
        event!!.acceptDrop(DnDConstants.ACTION_COPY)
        if (event.transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            val list = event.transferable.getTransferData(DataFlavor.javaFileListFlavor) as List<*>
            val files = list.stream().map { it as File }.collect(Collectors.toList())
            event.dropComplete(FileLoader.load(files))
            return
        }
        event.dropComplete(false)
    }

    override fun dropActionChanged(event: DropTargetDragEvent?) {}
    override fun dragExit(event: DropTargetEvent?) {}

}