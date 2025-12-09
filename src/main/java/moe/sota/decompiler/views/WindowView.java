package moe.sota.decompiler.views;

import com.formdev.flatlaf.extras.components.FlatSplitPane;
import com.formdev.flatlaf.util.SystemInfo;
import lombok.Getter;
import lombok.SneakyThrows;
import moe.sota.decompiler.controllers.StartController;
import moe.sota.decompiler.controllers.TabsController;
import moe.sota.decompiler.controllers.TreeController;
import moe.sota.decompiler.menus.MenuBar;
import moe.sota.decompiler.services.LanguageService;
import moe.sota.decompiler.services.LoaderService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.List;

@Getter
public class WindowView extends JFrame {

    public final FlatSplitPane splitPane;
    @Nullable
    private JPanel macos;

    public WindowView() {
        addComponentListener(new WindowComponentAdapter());
        setContentPane((Container) new StartController().getComponent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setDropTarget(new WindowDropTarget());
        setJMenuBar(KoinJavaComponent.get(MenuBar.class));
        setMinimumSize(new Dimension(500, 300));
        setPreferredSize(new Dimension(1000, 600));
        setTitle("Decompiler");

        String logo = SystemInfo.isMacOS ? "logo/logo-macos.png" : "logo/logo.png";
        Image image = Toolkit.getDefaultToolkit().createImage(LanguageService.class.getClassLoader().getResource(logo));
        if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.ICON_IMAGE))
            Taskbar.getTaskbar().setIconImage(image);
        setIconImage(image);

        splitPane = new FlatSplitPane();
        splitPane.setDividerLocation(225);
        splitPane.setRightComponent(TabsController.getINSTANCE().getComponent());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setMinimumSize(new Dimension(100, 0));
        splitPane.setLeftComponent(panel);

        if (SystemInfo.isMacFullWindowContentSupported) {
            getRootPane().putClientProperty("apple.awt.fullWindowContent", true);
            getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
            getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);

            Dimension dimension = new Dimension(0, 30);
            macos = new JPanel();
            macos.setMinimumSize(dimension);
            macos.setPreferredSize(dimension);
            panel.add(macos);
        }

        panel.add(TreeController.getINSTANCE().getComponent());

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void dispose() {
        super.dispose();
        System.exit(0);
    }

    private class WindowComponentAdapter extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent event) {
            if (SystemInfo.isMacOS && macos != null)
                macos.setVisible(getHeight() < GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight());
        }

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
                        LoaderService.loadAsync(file);
                        event.dropComplete(true);
                    }
                }
            }
        }

    }

}
