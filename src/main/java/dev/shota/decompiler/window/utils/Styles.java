package dev.shota.decompiler.window.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import java.util.Base64;
import java.util.Objects;

@UtilityClass
public class Styles {

    @SneakyThrows
    public static String get(String name) {
        byte[] css = Objects.requireNonNull(Styles.class.getClassLoader().getResourceAsStream(String.format("styles/%s.css", name))).readAllBytes();
        String base64 = Base64.getEncoder().encodeToString(css);
        return String.format("data:text/css;base64,%s", base64);
    }

}