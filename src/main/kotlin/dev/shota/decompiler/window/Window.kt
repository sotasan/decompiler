package dev.shota.decompiler.window

import com.formdev.flatlaf.util.SystemInfo
import com.sun.javafx.tk.Toolkit
import dev.shota.decompiler.window.container.Container
import dev.shota.decompiler.window.menu.MenuBar
import dev.shota.decompiler.window.sidebar.Sidebar
import dev.shota.decompiler.window.utils.styles
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.control.SplitPane
import javafx.scene.text.Font
import java.awt.Taskbar
import java.awt.datatransfer.DataFlavor
import java.awt.dnd.*
import java.io.File
import javax.swing.ImageIcon
import javax.swing.JFrame
import kotlin.system.exitProcess

object Window : JFrame() {

    private val fonts = arrayOf(
        "JetBrainsMono-Bold", "JetBrainsMono-BoldItalic", "JetBrainsMono-ExtraBold", "JetBrainsMono-ExtraBoldItalic",
        "JetBrainsMono-ExtraLight", "JetBrainsMono-ExtraLightItalic", "JetBrainsMono-Italic", "JetBrainsMono-Light",
        "JetBrainsMono-LightItalic", "JetBrainsMono-Medium", "JetBrainsMono-MediumItalic", "JetBrainsMono-Regular",
        "JetBrainsMono-SemiBold", "JetBrainsMono-SemiBoldItalic", "JetBrainsMono-Thin", "JetBrainsMono-ThinItalic",

        "OpenSans-Bold", "OpenSans-BoldItalic", "OpenSans-CondensedBold", "OpenSans-CondensedBoldItalic",
        "OpenSans-CondensedExtraBold", "OpenSans-CondensedExtraBoldItalic", "OpenSans-CondensedItalic",
        "OpenSans-CondensedLight", "OpenSans-CondensedLightItalic", "OpenSans-CondensedRegular",
        "OpenSans-CondensedSemiBold", "OpenSans-CondensedSemiBoldItalic", "OpenSans-ExtraBold",
        "OpenSans-ExtraBoldItalic", "OpenSans-Light", "OpenSans-LightItalic", "OpenSans-Regular",
        "OpenSans-SemiBold", "OpenSans-SemiBoldItalic"
    )

    init {
        title = "Decompiler"
        defaultCloseOperation = DISPOSE_ON_CLOSE
        val icon = ImageIcon(javaClass.classLoader.getResourceAsStream("logo/logo.png")?.readAllBytes()).image
        if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.ICON_IMAGE)) Taskbar.getTaskbar().iconImage = icon
        iconImage = icon
        jMenuBar = MenuBar()

        if (SystemInfo.isMacOS && SystemInfo.isMacFullWindowContentSupported) {
            rootPane.putClientProperty("apple.awt.fullWindowContent", true)
            rootPane.putClientProperty("apple.awt.transparentTitleBar", true)
            rootPane.putClientProperty("apple.awt.windowTitleVisible", false)
        }

        dropTarget = object : DropTarget() {
            override fun dragOver(event: DropTargetDragEvent?) {
                event!!.acceptDrag(DnDConstants.ACTION_COPY)
            }

            override fun drop(event: DropTargetDropEvent?) {
                event!!.acceptDrop(DnDConstants.ACTION_COPY)
                if (event.transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    val files = event.transferable.getTransferData(DataFlavor.javaFileListFlavor) as List<*>
                    if (files.isNotEmpty()) {
                        val file = files.first() as File
                        if (file.extension.equals("jar", true) ||
                            file.extension.equals("war", true) ||
                            file.extension.equals("zip", true)) {
                            Platform.runLater { Sidebar.open(file) }
                            event.dropComplete(true)
                            return
                        }
                    }
                }
                event.dropComplete(false)
            }
        }

        for (font in fonts)
            Font.loadFont(javaClass.classLoader.getResourceAsStream("fonts/${font.split("-")[0]}/$font.ttf"), Toolkit.getToolkit().fontLoader.systemFontSize.toDouble())

        val root = SplitPane(Sidebar, Container).apply {
            setDividerPositions(Sidebar.minWidth / (Sidebar.minWidth + Container.minWidth), Container.minWidth / (Sidebar.minWidth + Container.minWidth))
            SplitPane.setResizableWithParent(Sidebar, false)
            stylesheets.add(styles("global.styl"))
            stylesheets.add(styles("syntax.styl"))
        }

        val panel = JFXPanel().apply {
            scene = Scene(root, 894.0, 528.0)
            dropTarget.isActive = false
        }

        add(panel)
        pack()
        setLocationRelativeTo(null)
    }

    override fun dispose() {
        super.dispose()
        exitProcess(0)
    }

}