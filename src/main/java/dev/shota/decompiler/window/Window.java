package dev.shota.decompiler.window;

import com.formdev.flatlaf.util.SystemInfo;
import com.sun.javafx.tk.Toolkit;
import dev.shota.decompiler.loader.FileLoader;
import dev.shota.decompiler.reflection.Instance;
import dev.shota.decompiler.reflection.Singleton;
import dev.shota.decompiler.window.explorer.Explorer;
import dev.shota.decompiler.window.menu.MenuBar;
import dev.shota.decompiler.window.utils.Styles;
import dev.shota.decompiler.window.viewer.Viewer;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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
import java.util.stream.Collectors;

@Singleton
public class Window extends JFrame implements DropTargetListener {

    @SneakyThrows
    private Window() {
        Set<String> fonts = new Reflections("fonts", Scanners.Resources).getResources(Pattern.compile(".*\\.ttf"));
        for (String font : fonts)
            Font.loadFont(getClass().getClassLoader().getResourceAsStream(font), Toolkit.getToolkit().getFontLoader().getSystemFontSize());

        Region explorer = Instance.get(Explorer.class);
        Region viewer = Instance.get(Viewer.class);
        BorderPane sidebar = new BorderPane(explorer);
        SplitPane root = new SplitPane(sidebar, viewer);
        SplitPane.setResizableWithParent(sidebar, false);
        root.getStylesheets().addAll(Styles.get("main"), Styles.get("code"));
        root.setDividerPositions(
                explorer.getMinWidth() / (explorer.getMinWidth() + viewer.getMinWidth()),
                viewer.getMinWidth() / (explorer.getMinWidth() + viewer.getMinWidth())
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
        panel.getDropTarget().setActive(false);
        add(panel);
        pack();
        setTitle("Decompiler");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setJMenuBar(new MenuBar());
        setLocationRelativeTo(null);
    }

    @Override
    public void dispose() {
        super.dispose();
        System.exit(0);
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
        if (event.getTransferable().isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            List<?> list = (List<?>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
            List<File> files = list.stream().map(o -> (File) o).collect(Collectors.toList());
            event.dropComplete(FileLoader.load(files));
            return;
        }
        event.dropComplete(false);
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent event) {}
    @Override
    public void dragExit(DropTargetEvent event) {}

}