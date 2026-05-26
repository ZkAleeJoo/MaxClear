package clp.zkaleejoo;

import clp.zkaleejoo.commands.MainCommand;
import clp.zkaleejoo.config.MainConfigManager;
import clp.zkaleejoo.listeners.EntityListener;
import clp.zkaleejoo.managers.TaskManager;
import clp.zkaleejoo.utils.MessageUtils;
import clp.zkaleejoo.utils.UpdateChecker;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class MaxClear extends JavaPlugin {

    public static String prefix = "&e&lMaxClear &8» ";
    private MainConfigManager mainConfigManager;
    private TaskManager taskManager;
    private static final int BSTATS_PLUGIN_ID = 31581;
    private static final long UPDATE_CHECK_INTERVAL_TICKS = 20L * 60L * 60L * 5L;
    private Metrics metrics;
    private BukkitTask updateCheckTask;
    private String latestVersion;

    // PLUGIN ENCIENDE
    @Override
    public void onEnable() {
        mainConfigManager = new MainConfigManager(this);
        registerCommands();
        syncMetricsState();
        registerEvents();
        taskManager = new TaskManager(this);
        taskManager.startTasks();

        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(
                prefix + "&e   _____      _____  ____  ____________ .____     ___________   _____ __________ "));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix
                + "&e  /     \\    /  _  \\ \\   \\/  /\\_   ___ \\|    |    \\_   _____/  /  _  \\\\______   \\"));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix
                + "&e /  \\ /  \\  /  /_\\  \\ \\     / /    \\  \\/|    |     |    __)_  /  /_\\  \\|       _/"));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix
                + "&e/    Y    \\/    |    \\/     \\ \\     \\___|    |___  |        \\/    |    \\    |   \\"));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(
                prefix + "&e\\____|__  /\\____|__  /___/\\  \\ \\______  /_______ \\/_______  /\\____|__  /____|_  /"));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix
                + "&e        \\/         \\/      \\_/        \\/        \\/        \\/         \\/       \\/ "));
        Bukkit.getConsoleSender()
                .sendMessage(MessageUtils.getColoredMessage(prefix + "&eIt was activated correctly in the version"));

        startUpdateChecks();
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
        PluginCommand command = this.getCommand("maxclear");
        if (command == null) {
            getLogger().severe(
                    "Command 'maxclear' is not defined in plugin.yml. Disabling plugin to prevent startup errors.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        command.setExecutor(mainCommand);
        command.setTabCompleter(mainCommand);
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

    private void startUpdateChecks() {
        if (updateCheckTask != null) {
            updateCheckTask.cancel();
            updateCheckTask = null;
        }

        if (!getMainConfigManager().isUpdateCheckEnabled()) {
            return;
        }

        checkUpdates();
        updateCheckTask = Bukkit.getScheduler().runTaskTimer(this, this::checkUpdates,
                UPDATE_CHECK_INTERVAL_TICKS, UPDATE_CHECK_INTERVAL_TICKS);
    }

    private void checkUpdates() {
        if (!getMainConfigManager().isUpdateCheckEnabled())
            return;

        new UpdateChecker(this).getVersion(version -> {
            if (this.getPluginMeta().getVersion().equalsIgnoreCase(version)) {
                this.latestVersion = null;
                Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(
                        "&e&lMaxClear &8» &aA check for updates was performed and nothing was found."));
            } else {
                this.latestVersion = version;

                Bukkit.getConsoleSender()
                        .sendMessage(MessageUtils.getColoredMessage("&e&lMaxClear &8» &f&lNEW VERSION: &7" + version));
                Bukkit.getConsoleSender().sendMessage(
                        MessageUtils.getColoredMessage(
                                "&e&lMaxClear &8» &fDownload it now at the following link: &7https://modrinth.com/plugin/maxclear"));
            }
        });
    }

    private void syncMetricsState() {
        if (mainConfigManager != null && mainConfigManager.isBStatsEnabled()) {
            if (metrics == null) {
                metrics = new Metrics(this, BSTATS_PLUGIN_ID);
            }
            return;
        }

        if (metrics != null) {
            metrics.shutdown();
            metrics = null;
        }
    }

    public String getLatestVersion() {
        return latestVersion;
    }

}
