package gg.lunar.hub.feature.pvpmode;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class PvPMode {
    private static final Set<Player> pvpers = new HashSet<>();

    public static boolean isInPvPMode(Player player) {
        return pvpers.contains(player);
    }

    public static void enable(Player player) {
        pvpers.add(player);
    }

    public static void disable(Player player) {
        pvpers.remove(player);
    }
}