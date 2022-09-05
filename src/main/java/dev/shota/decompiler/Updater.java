package dev.shota.decompiler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shota.decompiler.window.utils.Language;
import javafx.application.Platform;
import lombok.SneakyThrows;
import org.controlsfx.control.Notifications;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

public class Updater implements Runnable {

    @Override
    @SneakyThrows
    public void run() {
        String lastChecked = Main.PREFERENCES.get(getClass().getCanonicalName(), null);
        if (LocalDate.now().toString().equals(lastChecked)) return;

        URLConnection connection = new URL("https://api.github.com/repos/sho7a/Decompiler/releases").openConnection();

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

        Platform.runLater(() -> Notifications.create()
                .title("Decompiler")
                .text(Language.get("updater"))
                .onAction(event -> {
                    try {
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
                            Desktop.getDesktop().browse(new URI(release.get().htmlUrl));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .show());

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