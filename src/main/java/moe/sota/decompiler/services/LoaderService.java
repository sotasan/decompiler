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

            if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW)) {
                Taskbar.getTaskbar().setWindowProgressState(
                        (JFrame) WindowController.INSTANCE.getComponent(),
                        Taskbar.State.INDETERMINATE
                );
            }

            try {
                JarFile jar = new JarFile(file);
                Enumeration<JarEntry> entries = jar.entries();
                ArchiveModel archive = new ArchiveModel(file.getName());

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();

                    if (entry.isDirectory()) {
                        // Ensure the full package chain exists
                        ensurePackagePath(archive, name);
                    } else {
                        // Ensure parent folder chain exists, then add file
                        String parentDir = parentDir(name);
                        BaseModel parent = parentDir.isEmpty()
                                ? archive
                                : ensurePackagePath(archive, parentDir);

                        parent.getChildren().add(new FileModel(jar, entry));
                    }
                }

                WindowController.INSTANCE.activate();
                TabsController.getINSTANCE().clearTabs();
                TreeController.getINSTANCE().setArchive(archive);

            } catch (Exception e) {
                e.printStackTrace(System.err);
            }

            if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW)) {
                Taskbar.getTaskbar().setWindowProgressState(
                        (JFrame) WindowController.INSTANCE.getComponent(),
                        Taskbar.State.OFF
                );
            }

        });
    }

    /**
     * Returns the parent directory path ending with '/', or "" if none.
     * Example: "META-INF/MANIFEST.MF" -> "META-INF/"
     */
    private static @NotNull String parentDir(@NotNull String path) {
        int idx = path.lastIndexOf('/');
        if (idx < 0) return "";
        return path.substring(0, idx + 1);
    }

    /**
     * Ensures that all package nodes for a path exist.
     * Path should end with '/', e.g. "moe/sota/decompiler/".
     * Returns the deepest created/found PackageModel.
     */
    private static @NotNull BaseModel ensurePackagePath(@NotNull BaseModel root, @NotNull String dirPath) {
        String[] parts = dirPath.split("/");
        StringBuilder currentPath = new StringBuilder();
        BaseModel current = root;

        for (String part : parts) {
            if (part == null || part.isEmpty()) continue;

            currentPath.append(part).append("/");
            String p = currentPath.toString();

            PackageModel existing = findPackageChild(current, p);
            if (existing == null) {
                existing = new PackageModel(p);
                current.getChildren().add(existing);
            }
            current = existing;
        }

        return current;
    }

    private static PackageModel findPackageChild(@NotNull BaseModel parent, @NotNull String path) {
        for (BaseModel child : parent.getChildren()) {
            if (child instanceof PackageModel && path.equals(child.getPath())) {
                return (PackageModel) child;
            }
        }
        return null;
    }
}
