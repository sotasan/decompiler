package dev.shota.decompiler;

import com.formdev.flatlaf.FlatLightLaf;
import dev.shota.decompiler.loader.FileLoader;
import dev.shota.decompiler.window.Window;
import javafx.application.Platform;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.Optional;

public class Main {

    public static void main(String @NotNull [] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", "Decompiler");
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameAqua");
        FlatLightLaf.setup();
        Platform.startup(() -> {});
        Window.getInstance().setVisible(true);
        new Updater().start();
        if (args.length != 0)
            FileLoader.load(new File(args[0]));
    }

    @SneakyThrows
    public static void restart() {
        Optional<String> java = ProcessHandle.current().info().command();
        if (java.isEmpty()) return;
        String classPath = ManagementFactory.getRuntimeMXBean().getClassPath();
        String main = Main.class.getCanonicalName();
        new ProcessBuilder(java.get(), "-cp", classPath, main).start();
        System.exit(0);
    }

}