package dev.shota.decompiler.window.utils;

import lombok.experimental.UtilityClass;
import java.util.Locale;
import java.util.ResourceBundle;

@UtilityClass
public class Language {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("lang/language", Locale.getDefault());

    public static String get(String key) {
        return resourceBundle.getString(key);
    }

}