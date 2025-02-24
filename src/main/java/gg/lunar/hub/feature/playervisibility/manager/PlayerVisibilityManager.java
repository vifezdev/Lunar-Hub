package gg.lunar.hub.feature.playervisibility.manager;

import gg.lunar.hub.LunarHub;
import gg.lunar.hub.config.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class PlayerVisibilityManager {
    private final Set<UUID> hiddenPlayers = new HashSet<>();
    private final ConfigFile usersFile;

    public PlayerVisibilityManager() {
        this.usersFile = new ConfigFile(LunarHub.getInstance(), "data/users.yml");
        loadHiddenPlayers();
    }

    public boolean isHidingPlayers(Player player) {
        return hiddenPlayers.contains(player.getUniqueId());
    }

    public void setHidingPlayers(Player player, boolean hiding) {
        UUID uuid = player.getUniqueId();
        if (hiding) {
            hiddenPlayers.add(uuid);
        } else {
            hiddenPlayers.remove(uuid);
        }

        usersFile.getConfig().set("players." + uuid + ".hiding", hiding);
        usersFile.save();

        updateAllPlayers(player, hiding);
    }

    public void updateAllPlayers(Player player, boolean hiding) {
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (player.equals(target)) continue;

            if (hiding) {
                player.hidePlayer(target);
            } else {
                player.showPlayer(target);
            }
        }
    }

    public void updateOnJoin(Player player) {
        if (isHidingPlayers(player)) {
            updateAllPlayers(player, true);
        }

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (isHidingPlayers(online)) {
                online.hidePlayer(player);
            }
        }
    }

    private void loadHiddenPlayers() {
        if (!usersFile.getConfig().contains("players")) return;
        for (String key : usersFile.getConfig().getConfigurationSection("players").getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            if (usersFile.getConfig().getBoolean("players." + key + ".hiding", false)) {
                hiddenPlayers.add(uuid);
            }
        }
    }
}