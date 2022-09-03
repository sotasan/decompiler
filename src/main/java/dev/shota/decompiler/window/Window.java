package dev.shota.decompiler.window;

import com.formdev.flatlaf.util.SystemInfo;
import com.sun.javafx.tk.Toolkit;
import dev.shota.decompiler.loader.FileLoader;
import dev.shota.decompiler.window.explorer.Explorer;
import dev.shota.decompiler.window.utils.Style;
import dev.shota.decompiler.window.viewer.Viewer;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class Window extends JFrame implements DropTargetListener {

    private static Window INSTANCE;

    @SneakyThrows
    private Window() {
        Set<String> fonts = new Reflections("fonts", Scanners.Resources).getResources(Pattern.compile(".*\\.ttf"));
        for (String font : fonts)
            Font.loadFont(getClass().getClassLoader().getResourceAsStream(font), Toolkit.getToolkit().getFontLoader().getSystemFontSize());

        BorderPane sidebar = new BorderPane(Explorer.getInstance());
        SplitPane root = new SplitPane(sidebar, Viewer.getInstance());
        SplitPane.setResizableWithParent(sidebar, false);
        root.getStylesheets().addAll(new Style("main").getData(), new Style("code").getData(), new Style("theme").getData());
        root.setDividerPositions(
                Explorer.getInstance().getMinWidth() / (Explorer.getInstance().getMinWidth() + Viewer.getInstance().getMinWidth()),
                Viewer.getInstance().getMinWidth() / (Explorer.getInstance().getMinWidth() + Viewer.getInstance().getMinWidth())
        );

        if (SystemInfo.isMacOS && SystemInfo.isMacFullWindowContentSupported) {
            rootPane.putClientProperty("apple.awt.fullWindowContent", true);
            rootPane.putClientProperty("apple.awt.transparentTitleBar", true);
            rootPane.putClientProperty("apple.awt.windowTitleVisible", false);
            rootPane.putClientProperty("apple.awt.fullscreenable", true);
            Pane space = new Pane();
            space.setPrefHeight(25);
            sidebar.setTop(space);
        }

        Image logo = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("logo/logo.png")).readAllBytes()).getImage();
        if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.ICON_IMAGE))
            Taskbar.getTaskbar().setIconImage(logo);
        setIconImage(logo);

        DropTarget dropTarget = new DropTarget();
        dropTarget.addDropTargetListener(this);
        setDropTarget(dropTarget);

        JFXPanel panel = new JFXPanel();
        panel.setScene(new Scene(root, 894, 528));
        add(panel);
        pack();
        setTitle("Decompiler");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMenuBar(new MenuBar());
        setLocationRelativeTo(null);
    }

    public static Window getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Window();
        return INSTANCE;
    }

    @Override
    public void dragEnter(@NotNull DropTargetDragEvent event) {
        event.acceptDrag(DnDConstants.ACTION_COPY);
    }

    @Override
    public void dragOver(@NotNull DropTargetDragEvent event) {
        event.acceptDrag(DnDConstants.ACTION_COPY);
    }

    @Override
    @SneakyThrows
    public void drop(@NotNull DropTargetDropEvent event) {
        event.acceptDrop(DnDConstants.ACTION_COPY);
        if (!event.getTransferable().isDataFlavorSupported(DataFlavor.javaFileListFlavor)) return;
        Object data = event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
        List<?> files = (List<?>) data;
        for (Object file : files)
            FileLoader.load((File) file);
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent event) {}
    @Override
    public void dragExit(DropTargetEvent event) {}

}