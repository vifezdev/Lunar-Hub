package gg.lunar.hub.feature.buildmode;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class BuildMode {
    private static final Set<Player> builders = new HashSet<>();

    public static boolean isInBuildMode(Player player) {
        return builders.contains(player);
    }

    public static void enable(Player player) {
        builders.add(player);
    }

    public static void disable(Player player) {
        builders.remove(player);
    }
}