package com.hohltier.decompiler;

import com.formdev.flatlaf.FlatLightLaf;
import com.hohltier.decompiler.loader.FileLoader;
import com.hohltier.decompiler.window.Window;
import com.sun.javafx.tk.Toolkit;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.Resource;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.Objects;
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

    @SneakyThrows
    public static void main(String @NotNull [] args) {
        /* TODO
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", "Decompiler");
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameAqua");
        */

        /* TODO
        int fps = 0;
        for (GraphicsDevice screen : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices())
            if (fps < screen.getDisplayMode().getRefreshRate())
                fps = screen.getDisplayMode().getRefreshRate();
        System.setProperty("javafx.animation.pulse", String.valueOf(fps));
         */

        for (Resource font : new ClassGraph().acceptPaths("fonts").scan().getResourcesWithExtension("ttf")) {
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(font.getPath()))));
            javafx.scene.text.Font.loadFont(Main.class.getClassLoader().getResourceAsStream(font.getPath()), Toolkit.getToolkit().getFontLoader().getSystemFontSize());
        }
        UIManager.put("defaultFont", new Font("Inter", Font.PLAIN, (int) Toolkit.getToolkit().getFontLoader().getSystemFontSize()));

        FlatLightLaf.setup();
        Window.getInstance().setVisible(true);
        FileLoader.load(Stream.of(args).map(File::new).collect(Collectors.toList()));
    }

    @SneakyThrows
    public static void start() {
        Optional<String> java = ProcessHandle.current().info().command();
        if (java.isEmpty()) return;
        String classPath = ManagementFactory.getRuntimeMXBean().getClassPath();
        String main = Main.class.getCanonicalName();
        new ProcessBuilder(java.get(), "-cp", classPath, main).start();
    }

}