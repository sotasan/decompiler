package com.hohltier.decompiler.views;

import com.formdev.flatlaf.extras.components.FlatSplitPane;
import com.hohltier.decompiler.controllers.EmptyController;
import com.hohltier.decompiler.controllers.ExplorerController;
import com.hohltier.decompiler.controllers.ViewerController;
import com.hohltier.decompiler.services.LoaderService;
import com.hohltier.decompiler.services.LanguageService;
import com.hohltier.decompiler.menus.MenuBar;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;
import java.util.Objects;

@Getter
public class WindowView extends JFrame {

    private final FlatSplitPane splitPane;

    public WindowView() {
        setContentPane((Container) new EmptyController().getComponent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setDropTarget(new WindowDropTarget());
        setJMenuBar(new MenuBar());
        setMinimumSize(new Dimension(500, 300));
        setPreferredSize(new Dimension(1000, 600));
        setTitle("Decompiler");

        Image logo = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(LanguageService.class.getClassLoader().getResource("logo/logo.png")));
        if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.ICON_IMAGE))
            Taskbar.getTaskbar().setIconImage(logo);
        setIconImage(logo);

        splitPane = new FlatSplitPane();
        splitPane.setDividerLocation(225);
        splitPane.setLeftComponent(ExplorerController.getINSTANCE().getComponent());
        splitPane.setRightComponent(ViewerController.getINSTANCE().getComponent());

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void dispose() {
        super.dispose();
        System.exit(0);
    }

    private static class WindowDropTarget extends DropTarget {

        @Override
        public synchronized void dragOver(@NotNull DropTargetDragEvent event) {
            event.acceptDrag(DnDConstants.ACTION_MOVE);
        }

        @Override
        @SneakyThrows
        public synchronized void drop(@NotNull DropTargetDropEvent event) {
            event.acceptDrop(DnDConstants.ACTION_MOVE);
            if (event.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                List<?> files = (List<?>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                if (!files.isEmpty()) {
                    File file = (File) files.get(0);
                    String name = file.getName().toLowerCase();
                    if (name.endsWith(".jar") || name.endsWith(".war") || name.endsWith(".zip")) {
                        LoaderService.load(file);
                        event.dropComplete(true);
                    }
                }
            }
        }

    }

}