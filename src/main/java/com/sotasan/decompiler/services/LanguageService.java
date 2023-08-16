package com.sotasan.decompiler.services;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import java.util.Locale;
import java.util.ResourceBundle;

@UtilityClass
public class LanguageService {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("langs/language", Locale.getDefault());

    public static @NotNull String getTranslation(String key) {
        return resourceBundle.getString(key);
    }

}