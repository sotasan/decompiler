package moe.sota.decompiler.jvm.services;

import java.awt.*;
import java.io.File;
import java.util.Enumeration;
import java.util.concurrent.CompletableFuture;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import lombok.experimental.UtilityClass;
import moe.sota.decompiler.jvm.controllers.TabsController;
import moe.sota.decompiler.jvm.controllers.TreeController;
import moe.sota.decompiler.jvm.controllers.WindowController;
import moe.sota.decompiler.jvm.models.ArchiveModel;
import moe.sota.decompiler.jvm.models.BaseModel;
import moe.sota.decompiler.jvm.models.FileModel;
import moe.sota.decompiler.jvm.models.PackageModel;
import moe.sota.decompiler.jvm.views.WindowView;
import org.jetbrains.annotations.NotNull;
import org.koin.java.KoinJavaComponent;

@UtilityClass
public class LoaderService {
    public static void loadAsync(File file) {
        CompletableFuture.runAsync(() -> {
            TabsController tabsController = KoinJavaComponent.get(TabsController.class);
            TreeController treeController = KoinJavaComponent.get(TreeController.class);
            WindowController windowController = KoinJavaComponent.get(WindowController.class);
            WindowView windowView = KoinJavaComponent.get(WindowView.class);

            if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW))
                Taskbar.getTaskbar().setWindowProgressState(windowView, Taskbar.State.INDETERMINATE);

            try {

                JarFile jar = new JarFile(file);
                Enumeration<JarEntry> entries = jar.entries();
                ArchiveModel archive = new ArchiveModel(file.getName());

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    BaseModel packageModel = getChildByPath(archive, entry.getName());
                    if (entry.isDirectory()) packageModel.getChildren().add(new PackageModel(entry.getName()));
                    else packageModel.getChildren().add(new FileModel(jar, entry));
                }

                windowController.activate();
                tabsController.clearTabs();
                treeController.setArchive(archive);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }

            if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW))
                Taskbar.getTaskbar().setWindowProgressState(windowView, Taskbar.State.OFF);
        });
    }

    private static BaseModel getChildByPath(@NotNull BaseModel baseModel, String path) {
        for (BaseModel child : baseModel.getChildren())
            if (child instanceof PackageModel && path.startsWith(child.getPath())) return getChildByPath(child, path);

        return baseModel;
    }
}
