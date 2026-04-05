package clp.zkaleejoo;

import clp.zkaleejoo.commands.MainCommand;
import clp.zkaleejoo.config.MainConfigManager;
import clp.zkaleejoo.listeners.EntityListener;
import clp.zkaleejoo.managers.TaskManager;
import clp.zkaleejoo.utils.MessageUtils;
import clp.zkaleejoo.utils.UpdateChecker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MaxClear extends JavaPlugin {

    public static String prefix = "&e&lMaxClear &8» ";
    private MainConfigManager mainConfigManager;
    private TaskManager taskManager;

    // PLUGIN ENCIENDE
    @Override
    public void onEnable() {
        registerCommands();
        registerEvents();
        checkUpdates();
        taskManager = new TaskManager(this);
        taskManager.startTasks();

        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix + "&e   _____      _____  ____  ____________ .____     ___________   _____ __________ "));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix + "&e  /     \\    /  _  \\ \\   \\/  /\\_   ___ \\|    |    \\_   _____/  /  _  \\\\______   \\"));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix + "&e /  \\ /  \\  /  /_\\  \\ \\     / /    \\  \\/|    |     |    __)_  /  /_\\  \\|       _/"));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix + "&e/    Y    \\/    |    \\/     \\ \\     \\___|    |___  |        \\/    |    \\    |   \\"));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix + "&e\\____|__  /\\____|__  /___/\\  \\ \\______  /_______ \\/_______  /\\____|__  /____|_  /"));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix + "&e        \\/         \\/      \\_/        \\/        \\/        \\/         \\/       \\/ "));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix + "&eIt was activated correctly in the version &a"+ getPluginMeta().getVersion()));
    }

    @Override
    public void onDisable() {
        if (taskManager != null) {
            taskManager.stopTasks();
        }
        Bukkit.getConsoleSender()
                .sendMessage(MessageUtils.getColoredMessage(prefix + "&eIt was successfully deactivated"));
    }

    public void registerCommands() {
        MainCommand mainCommand = new MainCommand(this);
        if (this.getCommand("maxclear") == null) {
            getLogger().severe(
                    "Command 'maxclear' is not defined in plugin.yml. Disabling plugin to prevent startup errors.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.getCommand("maxclear").setExecutor(mainCommand);
        this.getCommand("maxclear").setTabCompleter(mainCommand);
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
        new UpdateChecker(this).getVersion(version -> {
            if (this.getPluginMeta().getVersion().equalsIgnoreCase(version)) {
                getLogger().info("You are using the latest version!");
            } else {

                Bukkit.getConsoleSender()
                        .sendMessage(MessageUtils.getColoredMessage("&e&lMaxClear &8» &a&lUPDATE AVAILABLE!"));
                Bukkit.getConsoleSender().sendMessage(MessageUtils
                        .getColoredMessage("&e&lMaxClear &8» &7A new version of the plugin has been detected."));
                Bukkit.getConsoleSender().sendMessage(
                        MessageUtils.getColoredMessage("&e&lMaxClear &8» &7Available version: &a" + version));
                Bukkit.getConsoleSender().sendMessage(
                        MessageUtils.getColoredMessage("&e&lMaxClear &8» &eDownload it now at the following link:"));
                Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(
                        "&e&lMaxClear &8» &a&nhttps://modrinth.com/plugin/maxclear"));
            }
        });
    }

}