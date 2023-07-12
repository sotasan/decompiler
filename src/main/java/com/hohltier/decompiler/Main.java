package com.hohltier.decompiler;

import com.formdev.flatlaf.FlatLightLaf;
import com.hohltier.decompiler.controllers.WindowController;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.Resource;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
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

        FlatLightLaf.setup();
        UIManager.put("defaultFont", new Font("Inter", Font.PLAIN, ((FontUIResource) UIManager.get("defaultFont")).getSize()));

        WindowController.getINSTANCE().show();
        // TODO: FileLoader.load(Stream.of(args).map(File::new).collect(Collectors.toList()));
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