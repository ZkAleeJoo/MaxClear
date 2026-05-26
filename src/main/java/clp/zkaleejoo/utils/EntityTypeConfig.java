package clp.zkaleejoo.utils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class EntityTypeConfig {

    private static final Map<String, Set<String>> ALIASES = Map.ofEntries(
            Map.entry("BOAT", Set.of(
                    "OAK_BOAT",
                    "SPRUCE_BOAT",
                    "BIRCH_BOAT",
                    "JUNGLE_BOAT",
                    "ACACIA_BOAT",
                    "DARK_OAK_BOAT",
                    "MANGROVE_BOAT",
                    "CHERRY_BOAT",
                    "PALE_OAK_BOAT")),
            Map.entry("CHEST_BOAT", Set.of(
                    "OAK_CHEST_BOAT",
                    "SPRUCE_CHEST_BOAT",
                    "BIRCH_CHEST_BOAT",
                    "JUNGLE_CHEST_BOAT",
                    "ACACIA_CHEST_BOAT",
                    "DARK_OAK_CHEST_BOAT",
                    "MANGROVE_CHEST_BOAT",
                    "CHERRY_CHEST_BOAT",
                    "PALE_OAK_CHEST_BOAT")),
            Map.entry("MINECART_CHEST", Set.of("CHEST_MINECART")),
            Map.entry("MINECART_FURNACE", Set.of("FURNACE_MINECART")),
            Map.entry("MINECART_HOPPER", Set.of("HOPPER_MINECART")),
            Map.entry("MINECART_TNT", Set.of("TNT_MINECART")),
            Map.entry("MINECART_MOB_SPAWNER", Set.of("SPAWNER_MINECART")),
            Map.entry("MINECART_COMMAND", Set.of("COMMAND_BLOCK_MINECART")));

    private EntityTypeConfig() {
    }

    public static Set<String> resolveNames(Collection<String> configuredNames) {
        Set<String> resolvedNames = new LinkedHashSet<>();
        if (configuredNames == null) {
            return resolvedNames;
        }

        for (String configuredName : configuredNames) {
            if (configuredName == null) {
                continue;
            }

            String normalizedName = configuredName.trim().toUpperCase(Locale.ROOT);
            if (normalizedName.isEmpty()) {
                continue;
            }

            Set<String> aliases = ALIASES.get(normalizedName);
            if (aliases != null) {
                resolvedNames.addAll(aliases);
            } else {
                resolvedNames.add(normalizedName);
            }
        }
        return resolvedNames;
    }
}
