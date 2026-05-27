package clp.zkaleejoo.listeners;

import clp.zkaleejoo.MaxClear;
import clp.zkaleejoo.config.MainConfigManager;
import clp.zkaleejoo.utils.MessageUtils;
import clp.zkaleejoo.utils.UpdateNotificationFormatter;
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
        if (!player.hasPermission("maxclear.admin")) {
            return;
        }

        MainConfigManager config = plugin.getMainConfigManager();
        for (String line : UpdateNotificationFormatter.format(
                config.getPrefix(),
                config.getMsgUpdateAvailable(),
                config.getMsgUpdateCurrent(),
                config.getMsgUpdateDownload(),
                plugin.getPluginMeta().getVersion(),
                plugin.getLatestVersion(),
                MaxClear.UPDATE_DOWNLOAD_URL)) {
            player.sendMessage(MessageUtils.getColoredMessage(line));
        }
    }
}
