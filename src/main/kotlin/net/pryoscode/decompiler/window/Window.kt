package net.pryoscode.decompiler.window

import com.sun.javafx.tk.Toolkit
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.scene.text.Font
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import net.pryoscode.decompiler.window.container.Container
import net.pryoscode.decompiler.window.popup.About
import net.pryoscode.decompiler.window.sidebar.Sidebar
import net.pryoscode.decompiler.window.utils.style
import java.awt.Taskbar
import java.io.File
import javax.swing.*

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
        Taskbar.getTaskbar().iconImage = icon
        iconImage = icon

        val panel = JFXPanel()
        val root = BorderPane()
        root.center = Container
        root.left = Sidebar

        for (font in fonts)
            Font.loadFont(javaClass.classLoader.getResourceAsStream("fonts/${font.split("-")[0]}/$font.ttf"), Toolkit.getToolkit().fontLoader.systemFontSize.toDouble())
        root.stylesheets.add(style("base.less"))

        jMenuBar = JMenuBar()
        val file = JMenu("File")
        val fileOpen = JMenuItem("Open")
        val fileClose = JMenuItem("Close")
        val fileExit = JMenuItem("Exit")
        fileOpen.addActionListener {
            Platform.runLater {
                val fileChooser = FileChooser()
                fileChooser.extensionFilters.add(ExtensionFilter("Java Archive", "*.jar"))
                val file = fileChooser.showOpenDialog(panel.scene.window)
                Sidebar.open(file)
            }
        }
        fileClose.isEnabled = false
        fileExit.addActionListener { dispose() }
        file.add(fileOpen)
        file.add(fileClose)
        file.add(fileExit)
        jMenuBar.add(file)
        val help = JMenu("Help")
        val helpAbout = JMenuItem("About")
        helpAbout.addActionListener { About() }
        help.add(helpAbout)
        jMenuBar.add(help)

        /*
        val drop = DropTarget()
        drop.addDropTargetListener(object : DropTargetListener {
            override fun dropActionChanged(event: DropTargetDragEvent?) {}
            override fun dragExit(event: DropTargetEvent?) {}

            override fun dragEnter(event: DropTargetDragEvent?) {
                val files = event?.transferable?.getTransferData(DataFlavor.javaFileListFlavor) as List<File>
                if (files[0].extension.equals("jar", true))
                    event?.acceptDrag(DnDConstants.ACTION_MOVE)
                else
                    event?.rejectDrag()
            }

            override fun dragOver(event: DropTargetDragEvent?) {
                dragEnter(event)
            }

            override fun drop(event: DropTargetDropEvent?) {
                println("test")
                event?.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE)
                val files = event?.transferable?.getTransferData(DataFlavor.javaFileListFlavor) as List<File>
                if (files[0].extension.equals("jar", true)) {
                    Platform.runLater {
                        Sidebar.open(files[0])
                        SwingUtilities.invokeLater { event.dropComplete(true) }
                    }
                }
            }
        })
        dropTarget = drop
         */

        panel.scene = Scene(root, 896.0, 560.0)
        /*
        panel.scene.setOnDragOver {
            it.acceptTransferModes(*TransferMode.COPY_OR_MOVE)
            it.consume()
        }
        panel.scene.setOnDragDropped {
            println(it.dragboard.files.size)
            it.isDropCompleted = true
            it.consume()
        }
         */

        add(panel)
        pack()
        setLocationRelativeTo(null)
    }

    fun run(args: Array<String>) {
        isVisible = true
        Platform.runLater {
            if (args.isNotEmpty())
                Sidebar.open(File(args[0]))
        }
    }

}