package com.hohltier.decompiler;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatInspector;
import com.hohltier.decompiler.controllers.WindowController;
import com.hohltier.decompiler.loader.Loader;
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

@UtilityClass
public class Main {

    public static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
    public static final Preferences PREFERENCES = Preferences.userNodeForPackage(Main.class);

    @SneakyThrows
    public static void main(String @NotNull [] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", "Decompiler");
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameAqua");

        for (Resource font : new ClassGraph().acceptPaths("fonts").scan().getResourcesWithExtension("ttf"))
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.PLAIN, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(font.getPath()))));

        FlatLaf.registerCustomDefaultsSource("themes");
        FlatLightLaf.setup();
        UIManager.put("defaultFont", new Font("Inter", Font.PLAIN, UIManager.getFont("defaultFont").getSize()));

        FlatInspector.install("ctrl shift I");
        WindowController.getINSTANCE().show();
        if (args.length > 0)
            Loader.load(new File(args[0]));
    }

    @SneakyThrows
    public static void start() {
        Optional<String> java = ProcessHandle.current().info().command();
        if (java.isPresent()) {
            String classPath = ManagementFactory.getRuntimeMXBean().getClassPath();
            String main = Main.class.getCanonicalName();
            new ProcessBuilder(java.get(), "-cp", classPath, main).start();
        }
    }

}