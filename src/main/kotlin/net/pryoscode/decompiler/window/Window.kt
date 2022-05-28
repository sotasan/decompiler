package net.pryoscode.decompiler.window

import com.sun.javafx.tk.Toolkit
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.input.TransferMode
import javafx.scene.layout.BorderPane
import javafx.scene.text.Font
import net.pryoscode.decompiler.Main
import net.pryoscode.decompiler.window.container.Container
import net.pryoscode.decompiler.window.popup.About
import net.pryoscode.decompiler.window.sidebar.Sidebar
import net.pryoscode.decompiler.window.utils.styles
import java.awt.Taskbar
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.io.File
import java.lang.management.ManagementFactory
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter

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

        val panel = JFXPanel()
        val root = BorderPane()
        root.center = Container
        root.left = Sidebar

        for (font in fonts)
            Font.loadFont(javaClass.classLoader.getResourceAsStream("fonts/${font.split("-")[0]}/$font.ttf"), Toolkit.getToolkit().fontLoader.systemFontSize.toDouble())
        root.stylesheets.add(styles("global.less"))
        root.stylesheets.add(styles("syntax.less"))

        jMenuBar = JMenuBar()
        val file = JMenu("File")
        val fileOpenFile = JMenuItem("Open File", KeyEvent.VK_O)
        val fileCloseFile = JMenuItem("Close File", KeyEvent.VK_C)
        val fileNewWindow = JMenuItem("New Window", KeyEvent.VK_N)
        val fileExit = JMenuItem("Exit", KeyEvent.VK_Q)
        file.mnemonic = KeyEvent.VK_F
        fileOpenFile.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK)
        fileCloseFile.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK)
        fileNewWindow.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK)
        fileExit.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK)
        fileOpenFile.addActionListener {
            val fileChooser = JFileChooser()
            fileChooser.dialogTitle = fileOpenFile.text
            fileChooser.fileFilter = FileNameExtensionFilter("Java Archive", "jar")
            fileChooser.isAcceptAllFileFilterUsed = false
            fileChooser.showOpenDialog(this)
            Platform.runLater { Sidebar.open(fileChooser.selectedFile) }
        }
        fileCloseFile.isEnabled = false
        val function: (e: ActionEvent) -> Unit = {
            val java = ProcessHandle.current().info().command().get()
            val classPath = ManagementFactory.getRuntimeMXBean().classPath
            val main = Main.javaClass.declaringClass.canonicalName
            ProcessBuilder(java, "-cp", classPath, main).start()
        }
        fileNewWindow.addActionListener(function)
        fileExit.addActionListener { dispose() }
        file.add(fileOpenFile)
        file.add(fileCloseFile)
        file.add(fileNewWindow)
        file.add(fileExit)
        jMenuBar.add(file)
        val edit = JMenu("Edit")
        val editCopy = JMenuItem("Copy", KeyEvent.VK_C)
        val editSelectAll = JMenuItem("Select All", KeyEvent.VK_A)
        val editFind = JMenuItem("Find", KeyEvent.VK_F)
        edit.mnemonic = KeyEvent.VK_E
        editCopy.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK)
        editSelectAll.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK)
        editFind.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK)
        editCopy.isEnabled = false
        editSelectAll.isEnabled = false
        editFind.isEnabled = false
        edit.add(editCopy)
        edit.add(editSelectAll)
        edit.add(editFind)
        jMenuBar.add(edit)
        val view = JMenu("View")
        val viewZoomIn = JMenuItem("Zoom In", KeyEvent.VK_PLUS)
        val viewZoomOut = JMenuItem("Zoom Out", KeyEvent.VK_MINUS)
        val viewZoomReset = JMenuItem("Zoom Reset", KeyEvent.VK_0)
        view.mnemonic = KeyEvent.VK_V
        viewZoomIn.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, KeyEvent.CTRL_DOWN_MASK)
        viewZoomOut.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, KeyEvent.CTRL_DOWN_MASK)
        viewZoomReset.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_0, KeyEvent.CTRL_DOWN_MASK)
        viewZoomIn.isEnabled = false
        viewZoomOut.isEnabled = false
        viewZoomReset.isEnabled = false
        view.add(viewZoomIn)
        view.add(viewZoomOut)
        view.add(viewZoomReset)
        jMenuBar.add(view)
        val help = JMenu("Help")
        val helpAbout = JMenuItem("About", KeyEvent.VK_A)
        help.mnemonic = KeyEvent.VK_H
        helpAbout.addActionListener { About() }
        help.add(helpAbout)
        jMenuBar.add(help)

        panel.scene = Scene(root, 894.0, 506.0)
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

    fun run(args: Array<String>) {
        isVisible = true
        Platform.runLater {
            if (args.isNotEmpty())
                Sidebar.open(File(args[0]))
        }
    }

}