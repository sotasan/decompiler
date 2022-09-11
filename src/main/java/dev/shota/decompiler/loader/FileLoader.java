package dev.shota.decompiler.loader;

import dev.shota.decompiler.Main;
import dev.shota.decompiler.window.explorer.Explorer;
import dev.shota.decompiler.window.explorer.ExplorerEntry;
import dev.shota.decompiler.window.explorer.ExplorerRoot;
import dev.shota.decompiler.window.viewer.Code;
import dev.shota.decompiler.window.viewer.Viewer;
import javafx.application.Platform;
import javafx.scene.control.Tab;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileLoader implements Runnable {

    private final File file;

    private FileLoader(File file) {
        this.file = file;
    }

    @Override
    @SneakyThrows
    public void run() {
        if (file.getName().toLowerCase().endsWith(".class")) {
            byte[] bytes = Files.readAllBytes(file.toPath());
            Platform.runLater(() -> {
                Tab tab = new Code(file.getName(), bytes, true);
                Viewer.INSTANCE.getTabs().add(tab);
            });
            return;
        }

        Platform.runLater(() -> Viewer.INSTANCE.getTabs().clear());
        try (JarFile jar = new JarFile(file)) {
            List<ExplorerEntry> entries = new ArrayList<>();
            for (Iterator<JarEntry> it = jar.entries().asIterator(); it.hasNext();)
                entries.add(new ExplorerEntry(it.next()));
            Platform.runLater(() -> Explorer.INSTANCE.setRoot(new ExplorerRoot(new ExplorerEntry(jar), entries)));
        }
    }

    // TODO: multiple files
    public static boolean load(@NotNull List<File> files) {
        Optional<File> file = files.stream().findFirst();
        if (file.isEmpty() || !file.get().exists() || (
                !file.get().getName().toLowerCase().endsWith(".jar") &&
                !file.get().getName().toLowerCase().endsWith(".war") &&
                !file.get().getName().toLowerCase().endsWith(".zip") &&
                !file.get().getName().toLowerCase().endsWith(".class")))
            return false;

        Main.EXECUTOR.submit(new FileLoader(file.get()));

        return true;
    }

}