package clp.zkaleejoo.config;

import clp.zkaleejoo.MaxClear;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class CustomConfig {
    private MaxClear plugin;
    private String fileName;
    private FileConfiguration fileConfiguration = null;
    private File file = null;
    private String folderName;
    private boolean newFile;

    public CustomConfig(String fileName, String folderName, MaxClear plugin, boolean newFile) {
        this.fileName = fileName;
        this.folderName = folderName;
        this.plugin = plugin;
        this.newFile = newFile;
    }

    public String getPath() {
        return this.fileName;
    }

    public void registerConfig() {
        if (folderName != null) {
            File folder = new File(plugin.getDataFolder(), folderName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            file = new File(folder, fileName);
        } else {
            file = new File(plugin.getDataFolder(), fileName);
        }

        if (!file.exists()) {
            if (newFile) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (folderName != null) {
                    plugin.saveResource(folderName + "/" + fileName, false);
                } else {
                    plugin.saveResource(Objects.requireNonNull(fileName), false);
                }
            }

        }

        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);
            if (!newFile) {
                updateConfig();
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void updateConfig() {
        try (InputStream resourceStream = plugin.getResource(Objects.requireNonNull(getResourcePath()))) {
            if (resourceStream == null) {
                return;
            }

            YamlConfiguration bundledConfig = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(resourceStream, StandardCharsets.UTF_8));

            if (copyMissingDefaults(fileConfiguration, bundledConfig)) {
                saveConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean copyMissingDefaults(FileConfiguration fileConfiguration, FileConfiguration bundledConfig) {
        boolean changed = false;
        for (String key : bundledConfig.getKeys(true)) {
            if (!fileConfiguration.contains(key)) {
                fileConfiguration.set(key, bundledConfig.get(key));
                changed = true;
            }
        }
        return changed;
    }

    public void saveConfig() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
            reloadConfig();
        }
        return fileConfiguration;
    }

    public boolean reloadConfig() {
        if (folderName != null) {
            file = new File(plugin.getDataFolder() + File.separator + folderName, fileName);
        } else {
            file = new File(plugin.getDataFolder(), fileName);
        }

        fileConfiguration = YamlConfiguration.loadConfiguration(file);

        try (InputStream resourceStream = plugin.getResource(Objects.requireNonNull(getResourcePath()))) {
            if (resourceStream == null) {
                return true;
            }

            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(resourceStream, StandardCharsets.UTF_8));
            fileConfiguration.setDefaults(defConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private String getResourcePath() {
        return (folderName != null) ? folderName + "/" + fileName : fileName;
    }
}
