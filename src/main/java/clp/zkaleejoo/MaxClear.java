package clp.zkaleejoo;

import clp.zkaleejoo.commands.MainCommand;
import clp.zkaleejoo.config.MainConfigManager;
import clp.zkaleejoo.listeners.EntityListener;
import clp.zkaleejoo.managers.TaskManager;
import clp.zkaleejoo.utils.UpdateChecker;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MaxClear extends JavaPlugin {

    public static String prefix = "&e&lMaxClear &8» ";
    private MainConfigManager mainConfigManager;
    private TaskManager taskManager;
    private String latestVersion;

    //PLUGIN ENCIENDE
    @Override
    public void onEnable() {
        int pluginId = 28645;
        Metrics metrics = new Metrics(this, pluginId); 
        
        regsterCommands();
        registerEvents();
        mainConfigManager = new MainConfigManager(this);
        checkUpdates();
        taskManager = new TaskManager(this);
        taskManager.startTasks();

        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"   _____                _________ .__                       ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"  /     \\ _____  ___  __\\_   ___ \\|  |   ____ _____ _______ ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+" /  \\ /  \\\\__  \\ \\  \\/  /    \\  \\/|  | _/ __ \\\\__  \\\\_  __ \\");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"/    Y    \\/ __ \\_>    <\\     \\___|  |_\\  ___/ / __ \\|  | \\/");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"\\____|__  (____  /__/\\_ \\\\______  /____/\\___  >____  /__|   ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"        \\/     \\/      \\/       \\/          \\/     \\/       ");

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&eIt was activated correctly in the version"));
    }

    @Override
    public void onDisable() {
        taskManager.stopTasks();
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', prefix + "&eIt was successfully deactivated"));
    }

    public void regsterCommands() {
        MainCommand mainCommand = new MainCommand(this);
        this.getCommand("clearlagplus").setExecutor(new MainCommand(this));
        this.getCommand("clearlagplus").setTabCompleter(mainCommand);
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new EntityListener(this), this);
    }

    public MainConfigManager getMainConfigManager() {
        return mainConfigManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    private void checkUpdates() {
    if (!getMainConfigManager().isUpdateCheckEnabled()) return;

        new UpdateChecker(this).getVersion(version -> { 
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                getLogger().info("You are using the latest version!");
            } else {
                this.latestVersion = version;
                getLogger().warning("A new version is available: " + version);
                getLogger().warning("Download it at: https://modrinth.com/plugin/clearlag+"); 
            }
        });
    }


    public String getLatestVersion() { return latestVersion; }
}