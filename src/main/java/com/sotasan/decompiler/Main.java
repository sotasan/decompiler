package com.sotasan.decompiler;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatInspector;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import com.formdev.flatlaf.fonts.jetbrains_mono.FlatJetBrainsMonoFont;
import com.formdev.flatlaf.util.SystemInfo;
import com.sotasan.decompiler.controllers.WindowController;
import com.sotasan.decompiler.services.LoaderService;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import java.io.File;

@UtilityClass
public class Main {

    public static void main(String @NotNull [] args) {
        if (SystemInfo.isMacOS) {
            System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");
            System.setProperty("apple.awt.application.name", "Decompiler");
            System.setProperty("apple.laf.useScreenMenuBar", String.valueOf(true));
        }

        FlatInterFont.install();
        FlatJetBrainsMonoFont.install();
        FlatLaf.setPreferredFontFamily(FlatInterFont.FAMILY);
        FlatLaf.registerCustomDefaultsSource("themes");
        FlatDarkLaf.setup();
        FlatInspector.install(String.format("%s shift I", SystemInfo.isMacOS ? "meta" : "ctrl"));

        WindowController.getINSTANCE().show();
        if (args.length > 0)
            LoaderService.loadAsync(new File(args[0]));
    }

}