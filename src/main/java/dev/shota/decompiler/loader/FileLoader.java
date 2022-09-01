package dev.shota.decompiler.loader;

import dev.shota.decompiler.window.container.Code;
import dev.shota.decompiler.window.container.Container;
import javafx.application.Platform;
import javafx.scene.control.Tab;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import static com.rainerhahnekamp.sneakythrow.Sneaky.sneak;

public class FileLoader {

    public static boolean load(@NotNull File file) {
        if (!file.getName().toLowerCase().endsWith(".jar") &&
            !file.getName().toLowerCase().endsWith(".war") &&
            !file.getName().toLowerCase().endsWith(".zip") &&
            !file.getName().toLowerCase().endsWith(".class"))
            return false;

        Platform.runLater(() -> {
            if (file.getName().toLowerCase().endsWith(".class")) {
                Tab tab = new Code(file.getName(), sneak(() -> Files.readAllBytes(file.toPath())), true);
                if (!Container.INSTANCE.getTabs().contains(tab))
                    Container.INSTANCE.getTabs().add(tab);
                return;
            }

            Container.INSTANCE.getTabs().clear();
            JarFile jar = sneak(() -> new JarFile(file));
            for (Iterator<JarEntry> it = jar.entries().asIterator(); it.hasNext(); ) {
                JarEntry entry = it.next();
                System.out.println(entry.getName());
            }
            
        });
        return true;
    }

}
