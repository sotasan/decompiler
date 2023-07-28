package com.hohltier.decompiler.services;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import java.awt.*;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

@UtilityClass
public class ResourceService {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("langs/language", Locale.getDefault());

    public static Image getLogo() {
        return Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(ResourceService.class.getClassLoader().getResource("logo/logo.png")));
    }

    @SneakyThrows
    public static String getVersion() {
        Properties properties = new Properties();
        properties.load(ResourceService.class.getClassLoader().getResourceAsStream("application.properties"));
        return properties.getProperty("version");
    }

    public static @NotNull String getTranslation(String key) {
        return resourceBundle.getString(key);
    }

}