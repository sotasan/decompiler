package dev.shota.decompiler.window.utils;

import lombok.Getter;
import lombok.SneakyThrows;
import java.util.Base64;
import java.util.Objects;

public class Style {

    @Getter
    private final String data;

    @SneakyThrows
    public Style(String name) {
        byte[] css = Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(String.format("styles/%s.css", name))).readAllBytes();
        String base64 = Base64.getEncoder().encodeToString(css);
        data = String.format("data:text/css;base64,%s", base64);
    }

}