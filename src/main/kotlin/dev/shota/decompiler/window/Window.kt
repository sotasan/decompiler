package dev.shota.decompiler.window

import com.sun.javafx.tk.Toolkit
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.control.SplitPane
import javafx.scene.input.TransferMode
import javafx.scene.text.Font
import dev.shota.decompiler.window.container.Container
import dev.shota.decompiler.window.menu.Menu
import dev.shota.decompiler.window.sidebar.Sidebar
import dev.shota.decompiler.window.utils.styles
import java.awt.Taskbar
import java.io.File
import javax.swing.*
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
        val icon = ImageIcon(javaClass.classLoader.getResourceAsStream("icons/logo.png")?.readAllBytes()).image
        if (Taskbar.getTaskbar().isSupported(Taskbar.Feature.ICON_IMAGE)) Taskbar.getTaskbar().iconImage = icon
        iconImage = icon
        jMenuBar = Menu()

        val panel = JFXPanel()
        val root = SplitPane(Sidebar, Container)
        root.setDividerPositions(Sidebar.minWidth / (Sidebar.minWidth + Container.minWidth), Container.minWidth / (Sidebar.minWidth + Container.minWidth))
        SplitPane.setResizableWithParent(Sidebar, false)

        for (font in fonts)
            Font.loadFont(javaClass.classLoader.getResourceAsStream("fonts/${font.split("-")[0]}/$font.ttf"), Toolkit.getToolkit().fontLoader.systemFontSize.toDouble())
        root.stylesheets.add(styles("global.less"))
        root.stylesheets.add(styles("syntax.less"))

        panel.scene = Scene(root, 894.0, 528.0)
        panel.scene.setOnDragOver {
            it.acceptTransferModes(*TransferMode.COPY_OR_MOVE)
            it.consume()
        }
        panel.scene.setOnDragDropped {
            if (it.dragboard.files.size > 0 && it.dragboard.files[0].extension.equals("jar", true)) {
                Sidebar.open(it.dragboard.files[0])
                it.isDropCompleted = true
            }
            it.consume()
        }

        add(panel)
        pack()
        setLocationRelativeTo(null)
    }

    override fun dispose() {
        super.dispose()
        exitProcess(0)
    }

    fun run(args: Array<String>) {
        isVisible = true
        Platform.runLater {
            if (args.isNotEmpty())
                Sidebar.open(File(args[0]))
        }
    }

}