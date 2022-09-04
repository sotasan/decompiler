package dev.shota.decompiler;

import com.formdev.flatlaf.FlatLightLaf;
import dev.shota.decompiler.loader.FileLoader;
import dev.shota.decompiler.window.Window;
import javafx.application.Platform;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import java.awt.*;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class Main {

    public static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
    public static final Preferences PREFERENCES = Preferences.userNodeForPackage(Main.class);

    public static void main(String @NotNull [] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", "Decompiler");
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameAqua");

        int fps = 0;
        for (GraphicsDevice screen : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices())
            if (fps < screen.getDisplayMode().getRefreshRate())
                fps = screen.getDisplayMode().getRefreshRate();
        System.setProperty("javafx.animation.pulse", String.valueOf(fps));

        Platform.startup(() -> {});
        FlatLightLaf.setup();
        Window.INSTANCE.setVisible(true);
        EXECUTOR.submit(new Updater());
        FileLoader.load(Stream.of(args).map(File::new).collect(Collectors.toList()));
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