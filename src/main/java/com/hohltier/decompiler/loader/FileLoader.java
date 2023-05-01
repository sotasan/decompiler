package com.hohltier.decompiler.loader;

import com.hohltier.decompiler.Main;
import com.hohltier.decompiler.jvm.Decompiler;
import com.hohltier.decompiler.window.explorer.Explorer;
import com.hohltier.decompiler.window.viewer.ViewerCode;
import com.hohltier.decompiler.window.viewer.Viewer;
import javafx.application.Platform;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileLoader implements Runnable {

    private final File file;
    private final FileType fileType;

    private FileLoader(File file, FileType fileType) {
        this.file = file;
        this.fileType = fileType;
    }

    @Override
    @SneakyThrows
    public void run() {
        if (fileType == FileType.CLASS) {
            byte[] bytes = Files.readAllBytes(file.toPath());
            String text = new Decompiler(bytes).getCode();
            Platform.runLater(() -> Viewer.getInstance().getTabs().add(new ViewerCode(file.getName(), text)));
            return;
        }

        if (fileType == FileType.ARCHIVE) {
            JarFile jar = new JarFile(file);
            for (JarEntry entry : jar.stream().toList()) {
                System.out.println(entry.getName());
            }
        }

        /*
        if (file.getName().toLowerCase().endsWith(".class")) {
            byte[] bytes = Files.readAllBytes(file.toPath());

            return;
        }
         */

        // Platform.runLater(() -> Viewer.INSTANCE.getTabs().clear());
        // Platform.runLater(() -> Explorer.INSTANCE.setRoot(new ExplorerRoot(new ExplorerEntry(jar), entries)));
    }

    public static void load(@NotNull List<File> files) {
        Explorer.getInstance().setEnabled(false);
        for (File file : files) {
            if (!file.exists())
                continue;

            FileType fileType = null;

            if (file.getName().toLowerCase().endsWith(".class"))
                fileType = FileType.CLASS;

            if (file.getName().toLowerCase().endsWith(".jar") ||
                file.getName().toLowerCase().endsWith(".war") ||
                file.getName().toLowerCase().endsWith(".zip")) {
                fileType = FileType.ARCHIVE;
                Explorer.getInstance().setEnabled(true);
            }

            if (fileType == null)
                continue;

            Main.EXECUTOR.submit(new FileLoader(file, fileType));
        }
    }

}