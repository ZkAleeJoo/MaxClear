package clp.zkaleejoo.managers;

import clp.zkaleejoo.MaxClear;
import clp.zkaleejoo.utils.EntityClearer;
import clp.zkaleejoo.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskManager {

    private final MaxClear plugin;
    private final EntityClearer entityClearer;
    private int taskId = -1;
    private int warningTaskId = -1;

    public TaskManager(MaxClear plugin) {
        this.plugin = plugin;
        this.entityClearer = new EntityClearer(plugin);
    }

    public void startTasks() {
        ClearSchedule schedule = ClearSchedule.from(
                plugin.getMainConfigManager().getAutoClearEnabled(),
                plugin.getMainConfigManager().getAutoClearInterval(),
                plugin.getMainConfigManager().getWarningEnabled(),
                plugin.getMainConfigManager().getWarningSecondsBefore());

        if (schedule.warningReason() != null) {
            plugin.getLogger().warning(schedule.warningReason());
        }

        if (schedule.clearEnabled()) {
            taskId = new BukkitRunnable() {
                @Override
                public void run() {
                    entityClearer.clearEntities(false, null);
                }
            }.runTaskTimer(plugin, schedule.clearDelayTicks(), schedule.clearDelayTicks()).getTaskId();
        }

        if (schedule.warningEnabled()) {
            int warningTime = plugin.getMainConfigManager().getWarningSecondsBefore();

            warningTaskId = new BukkitRunnable() {
                @Override
                public void run() {
                    String message = plugin.getMainConfigManager().getWarningMessage()
                            .replace("{time}", String.valueOf(warningTime));
                    MessageUtils.broadcastToPlayersOnly(plugin.getMainConfigManager().getPrefix() + message);
                }
            }.runTaskTimer(plugin, schedule.warningDelayTicks(), schedule.clearDelayTicks()).getTaskId();
        }
    }

    public void stopTasks() {
        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = -1;
        }
        if (warningTaskId != -1) {
            Bukkit.getScheduler().cancelTask(warningTaskId);
            warningTaskId = -1;
        }
    }

    public void reloadTasks() {
        stopTasks();
        startTasks();
    }
}
