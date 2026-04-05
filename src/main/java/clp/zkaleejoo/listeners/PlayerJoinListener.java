package clp.zkaleejoo.listeners;

import clp.zkaleejoo.MaxClear;
import clp.zkaleejoo.config.MainConfigManager;
import clp.zkaleejoo.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final MaxClear plugin;

    public PlayerJoinListener(MaxClear plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("maxclear.admin")) return;

        String latest = plugin.getLatestVersion();
        MainConfigManager config = plugin.getMainConfigManager();

        if (latest != null && !plugin.getDescription().getVersion().equalsIgnoreCase(latest)) {
            player.sendMessage(MessageUtils.getColoredMessage(
                config.getPrefix() + config.getMsgUpdateAvailable().replace("{version}", latest)));
        }
    }
}