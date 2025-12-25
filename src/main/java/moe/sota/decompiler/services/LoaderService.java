package moe.sota.decompiler.services;

import lombok.experimental.UtilityClass;
import moe.sota.decompiler.controllers.TabsController;
import moe.sota.decompiler.controllers.TreeController;
import moe.sota.decompiler.controllers.WindowController;
import moe.sota.decompiler.models.ArchiveModel;
import moe.sota.decompiler.models.BaseModel;
import moe.sota.decompiler.models.FileModel;
import moe.sota.decompiler.models.PackageModel;
import org.jetbrains.annotations.NotNull;
import org.koin.java.KoinJavaComponent;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Enumeration;
import java.util.concurrent.CompletableFuture;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@UtilityClass
public class LoaderService {

    public static void loadAsync(File file) {
        CompletableFuture.runAsync(() -> {
            WindowController windowController = KoinJavaComponent.get(WindowController.class);

            if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW))
                Taskbar.getTaskbar().setWindowProgressState((JFrame) windowController.getComponent(), Taskbar.State.INDETERMINATE);

            try {

                JarFile jar = new JarFile(file);
                Enumeration<JarEntry> entries = jar.entries();
                ArchiveModel archive = new ArchiveModel(file.getName());

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    BaseModel packageModel = getChildByPath(archive, entry.getName());
                    if (entry.isDirectory())
                        packageModel.getChildren().add(new PackageModel(entry.getName()));
                    else
                        packageModel.getChildren().add(new FileModel(jar, entry));
                }

                windowController.activate();
                TabsController.getINSTANCE().clearTabs();
                TreeController.getINSTANCE().setArchive(archive);

            } catch (Exception e) {
                e.printStackTrace(System.err);
            }

            if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW))
                Taskbar.getTaskbar().setWindowProgressState((JFrame) windowController.getComponent(), Taskbar.State.OFF);

        });
    }

    private static BaseModel getChildByPath(@NotNull BaseModel baseModel, String path) {
        for (BaseModel child : baseModel.getChildren())
            if (child instanceof PackageModel && path.startsWith(child.getPath()))
                return getChildByPath(child, path);
        return baseModel;
    }

}
