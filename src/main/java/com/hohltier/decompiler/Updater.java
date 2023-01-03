package com.hohltier.decompiler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hohltier.decompiler.window.utils.Language;
import lombok.SneakyThrows;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

public class Updater implements Runnable {

    @Override
    @SneakyThrows
    public void run() {
        if (!SystemTray.isSupported()) return;
        String lastChecked = Main.PREFERENCES.get(getClass().getCanonicalName(), null);
        if (LocalDate.now().toString().equals(lastChecked)) return;

        URLConnection connection = new URL("https://api.github.com/repos/hohltier/decompiler/releases").openConnection();

        try {
            connection.connect();
        } catch (IOException e) {
            return;
        }

        InputStream inputStream = connection.getInputStream();
        Release[] releases = new ObjectMapper().readValue(inputStream, Release[].class);
        Optional<Release> release = Arrays.stream(releases).findFirst();
        if (release.isEmpty()) return;

        String[] currentArray = getVersion().split("\\.");
        int currentMajor = Integer.parseInt(currentArray[0]);
        int currentMinor = Integer.parseInt(currentArray[1]);
        int currentPatch = Integer.parseInt(currentArray[2]);
        Version current = new Version(currentMajor, currentMinor, currentPatch, null, null, null);

        String[] originArray = release.get().tagName.substring(1).split("\\.");
        int originMajor = Integer.parseInt(originArray[0]);
        int originMinor = Integer.parseInt(originArray[1]);
        int originPatch = Integer.parseInt(originArray[2]);
        Version origin = new Version(originMajor, originMinor, originPatch, null, null, null);

        if (current.compareTo(origin) >= 0)
            return;

        TrayIcon trayIcon = new TrayIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("logo/logo.png")).readAllBytes()).getImage());
        SystemTray.getSystemTray().add(trayIcon);
        trayIcon.setToolTip(Language.get("updater"));
        trayIcon.setImageAutoSize(true);
        trayIcon.displayMessage("Decompiler", trayIcon.getToolTip(), TrayIcon.MessageType.INFO);
        trayIcon.addActionListener(e -> {
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
                    Desktop.getDesktop().browse(new URI(release.get().htmlUrl));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Main.PREFERENCES.put(getClass().getCanonicalName(), LocalDate.now().toString());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Release {

        @JsonProperty("html_url")
        private String htmlUrl;

        @JsonProperty("tag_name")
        private String tagName;

    }

    @SneakyThrows
    public static String getVersion() {
        Properties properties = new Properties();
        properties.load(Updater.class.getClassLoader().getResourceAsStream("application.properties"));
        return properties.getProperty("version");
    }

}