package clp.zkaleejoo.utils;

import org.bukkit.Bukkit;
import clp.zkaleejoo.MaxClear;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class UpdateChecker {

    private static final String GITHUB_VERSION_URL = "https://gist.githubusercontent.com/ZkAleeJoo/9680c607a4a1ef52587e635357945b8c/raw/MaxClear-Version.txt";
    private final MaxClear plugin;

    public UpdateChecker(MaxClear plugin) {
        this.plugin = plugin;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            HttpURLConnection connection = null;
            try {
                URL url = URI.create(GITHUB_VERSION_URL).toURL();
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "MaxClear-UpdateChecker");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                int statusCode = connection.getResponseCode();
                if (statusCode < 200 || statusCode >= 300) {
                    plugin.getLogger().warning("Could not check updates. HTTP " + statusCode);
                    return;
                }

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    String latestVersion = reader.readLine();
                    if (latestVersion != null && !latestVersion.isBlank()) {
                        String trimmedVersion = latestVersion.trim();
                        if (plugin.isEnabled()) {
                            Bukkit.getScheduler().runTask(plugin, () -> consumer.accept(trimmedVersion));
                        }
                    } else {
                        plugin.getLogger().info("The version is empty.");
                    }
                }
            } catch (Exception exception) {
                plugin.getLogger().info("Could not connect to check for updates: " + exception.getMessage());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
    }
}
