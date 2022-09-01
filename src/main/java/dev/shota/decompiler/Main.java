package dev.shota.decompiler;

import com.formdev.flatlaf.FlatLightLaf;
import dev.shota.decompiler.loader.FileLoader;
import dev.shota.decompiler.window.Window;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;
import java.io.File;

public class Main {

    public static void main(String @NotNull [] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", "Decompiler");
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameAqua");
        FlatLightLaf.setup();
        Platform.startup(() -> {});
        Window.INSTANCE.setVisible(true);
        new Updater().start();
        if (args.length != 0)
            FileLoader.load(new File(args[0]));
    }

}