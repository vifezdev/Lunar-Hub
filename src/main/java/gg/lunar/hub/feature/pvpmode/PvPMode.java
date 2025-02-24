package gg.lunar.hub.feature.pvpmode;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class PvPMode {
    private static final Set<UUID> pvpers = new HashSet<>();

    public static boolean isInPvPMode(Player player) {
        return pvpers.contains(player.getUniqueId());
    }

    public static void enable(Player player) {
        pvpers.add(player.getUniqueId());
    }

    public static void disable(Player player) {
        pvpers.remove(player.getUniqueId());
    }

    public static void toggle(Player player) {
        if (isInPvPMode(player)) {
            disable(player);
        } else {
            enable(player);
        }
    }
}