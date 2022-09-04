package dev.shota.decompiler.loader;

import dev.shota.decompiler.Main;
import dev.shota.decompiler.reflection.Instance;
import dev.shota.decompiler.window.container.Code;
import dev.shota.decompiler.window.viewer.Viewer;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileLoader implements Runnable {

    private final File file;

    private FileLoader(@NotNull File file) {
        this.file = file;
    }

    @Override
    @SneakyThrows
    public void run() {
        if (file.getName().toLowerCase().endsWith(".class")) {
            Tab tab = new Code(file.getName(), Files.readAllBytes(file.toPath()), true);
            TabPane viewer = Instance.get(Viewer.class);
            if (!viewer.getTabs().contains(tab))
                viewer.getTabs().add(tab);
            return;
        }

        Instance.get(Viewer.class).getTabs().clear();
        try (JarFile jar = new JarFile(file)) {
            for (Iterator<JarEntry> it = jar.entries().asIterator(); it.hasNext(); ) {
                JarEntry entry = it.next();
                System.out.println(entry.getName());
            }
        }
    }

    public static boolean load(@NotNull File file) {
        if (!file.exists() || (
                !file.getName().toLowerCase().endsWith(".jar") &&
                !file.getName().toLowerCase().endsWith(".war") &&
                !file.getName().toLowerCase().endsWith(".zip") &&
                !file.getName().toLowerCase().endsWith(".class")))
            return false;

        Main.EXECUTOR.submit(new FileLoader(file));

        return true;
    }

}