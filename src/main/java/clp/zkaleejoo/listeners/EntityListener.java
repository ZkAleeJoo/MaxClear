package clp.zkaleejoo.listeners;

import clp.zkaleejoo.MaxClear;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Arrays;

public class EntityListener implements Listener {

    private final MaxClear plugin;

    public EntityListener(MaxClear plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (!plugin.getMainConfigManager().isChunkLimitEnabled()) {
            return;
        }

        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) {
            return;
        }

        Entity entity = event.getEntity();
        
        if (entity instanceof Player || plugin.getMainConfigManager().getEntitiesExclude().contains(entity.getType().name())) {
            return;
        }

        Chunk chunk = event.getLocation().getChunk();
        int maxEntities = plugin.getMainConfigManager().getChunkLimitMax();
        
        long count = Arrays.stream(chunk.getEntities())
                .filter(e -> !(e instanceof Player))
                .filter(e -> !plugin.getMainConfigManager().getEntitiesExclude().contains(e.getType().name()))
                .count();

        if (count >= maxEntities) {
            event.setCancelled(true);
        }
    }
}