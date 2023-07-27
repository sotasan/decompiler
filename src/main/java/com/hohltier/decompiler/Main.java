package com.hohltier.decompiler;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatInspector;
import com.hohltier.decompiler.controllers.WindowController;
import com.hohltier.decompiler.services.LoaderService;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.Resource;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import java.awt.*;
import java.io.File;
import java.util.Objects;

@UtilityClass
public class Main {

    @SneakyThrows
    public static void main(String @NotNull [] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", "Decompiler");
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");

        for (Resource font : new ClassGraph().acceptPaths("fonts").scan().getResourcesWithExtension("ttf"))
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.PLAIN, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(font.getPath()))));

        FlatLaf.registerCustomDefaultsSource("themes");
        FlatDarkLaf.setup();

        FlatInspector.install("ctrl shift I");
        WindowController.getINSTANCE().show();
        if (args.length > 0)
            LoaderService.load(new File(args[0]));
    }

}