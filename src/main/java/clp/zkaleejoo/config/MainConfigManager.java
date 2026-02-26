package clp.zkaleejoo.config;

import clp.zkaleejoo.MaxClear;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MainConfigManager {

    private CustomConfig configFile;

    private String prefix;
    private Boolean autoClearEnabled;
    private Integer autoClearInterval;
    private Boolean warningEnabled;
    private Integer warningSecondsBefore;
    private String warningMessage;
    private List<String> completedMessage;
    private String manualClearMessage;
    private List<String> entitiesTypes;
    private List<String> entitiesExclude;
    private Boolean entitiesIgnoreNamedItems;
    private Integer entitiesMinDistanceFromSpawn;
    private List<String> entitiesWorlds;
    private String noPermission;
    private String pluginReload;
    private String messageConsole;
    private String subcommandInvalid;
    private String subcommandSpecified;
    private Boolean chunkLimitEnabled;
    private Integer chunkLimitMax;
    private boolean updateCheckEnabled;
    private String msgUpdateAvailable;

    public MainConfigManager(MaxClear plugin){
        configFile = new CustomConfig("config.yml", null, plugin, false);
        configFile.registerConfig();
        loadConfig();
    }

    public void loadConfig(){
        FileConfiguration config = configFile.getConfig();
        prefix = config.getString("general.prefix");
        autoClearEnabled = config.getBoolean("auto-clear.enabled");
        autoClearInterval = config.getInt("auto-clear.interval");
        warningEnabled = config.getBoolean("messages.warning.enabled");
        warningSecondsBefore = config.getInt("messages.warning.seconds-before");
        warningMessage = config.getString("messages.warning.message");
        completedMessage = config.getStringList("messages.completed.message");
        manualClearMessage = config.getString("messages.manual-clear.message");
        entitiesTypes = config.getStringList("entities.types");
        entitiesExclude = config.getStringList("entities.exclude");
        entitiesIgnoreNamedItems = config.getBoolean("entities.settings.ignore-named-items");
        entitiesMinDistanceFromSpawn = config.getInt("entities.settings.min-distance-from-spawn");
        entitiesWorlds = config.getStringList("entities.settings.worlds");
        noPermission = config.getString("messages.no-permission");
        pluginReload = config.getString("messages.plugin-reload");
        messageConsole = config.getString("messages.message-console");
        subcommandInvalid = config.getString("messages.subcommand-invalid");
        subcommandSpecified = config.getString("messages.subcommand-specified");

        chunkLimitEnabled = config.getBoolean("chunk-limit.enabled");
        chunkLimitMax = config.getInt("chunk-limit.max");
        updateCheckEnabled = config.getBoolean("general.update-check", true);
        msgUpdateAvailable = config.getString("messages.update-available");
    }

    public void reloadConfig(){
        configFile.reloadConfig();
        loadConfig();
    }

    public String getPrefix() { return prefix; }
    public Boolean getAutoClearEnabled() { return autoClearEnabled; }
    public Integer getAutoClearInterval() { return autoClearInterval; }
    public Boolean getWarningEnabled() { return warningEnabled; }
    public Integer getWarningSecondsBefore() { return warningSecondsBefore; }
    public String getWarningMessage() { return warningMessage; }
    public List<String> getCompletedMessage() { return completedMessage; }
    public String getManualClearMessage() { return manualClearMessage; }
    public List<String> getEntitiesTypes() { return entitiesTypes; }
    public List<String> getEntitiesExclude() { return entitiesExclude; }
    public Boolean getEntitiesIgnoreNamedItems() { return entitiesIgnoreNamedItems; }
    public Integer getEntitiesMinDistanceFromSpawn() { return entitiesMinDistanceFromSpawn; }
    public List<String> getEntitiesWorlds() { return entitiesWorlds; }
    public String getNoPermission() { return noPermission; }
    public String getPluginReload() { return pluginReload; }
    public String getMessageConsole() { return messageConsole; }
    public String getSubcommandInvalid() { return subcommandInvalid; }
    public String getSubcommandSpecified() { return subcommandSpecified; }
    public Boolean isChunkLimitEnabled() { return chunkLimitEnabled; }
    public Integer getChunkLimitMax() { return chunkLimitMax; }
    public boolean isUpdateCheckEnabled() { return updateCheckEnabled; }
    public String getMsgUpdateAvailable() { return msgUpdateAvailable; }
}