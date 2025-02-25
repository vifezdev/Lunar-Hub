package gg.lunar.hub.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class User {

    private final UUID uuid;
    private int ping;
    private boolean scoreboardEnabled = true;
    private String name;
    private boolean vanished;
    private boolean flightEnabled;
    private boolean hidingPlayers;
    private boolean globalChatEnabled = true;

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.vanished = false;
        this.flightEnabled = false;
        this.hidingPlayers = false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVanished() {
        return vanished;
    }

    public void setVanished(boolean vanished) {
        this.vanished = vanished;
    }

    public boolean isFlightEnabled() {
        return flightEnabled;
    }

    public void setFlightEnabled(boolean flightEnabled) {
        this.flightEnabled = flightEnabled;
    }

    public boolean isHidingPlayers() {
        return hidingPlayers;
    }

    public boolean isScoreboardEnabled() {
        return scoreboardEnabled;
    }

    public void setScoreboardEnabled(boolean scoreboardEnabled) {
        this.scoreboardEnabled = scoreboardEnabled;
    }


    public void setHidingPlayers(boolean hidingPlayers) {
        this.hidingPlayers = hidingPlayers;
    }

    public boolean isGlobalChatEnabled() {
        return globalChatEnabled;
    }

    public void setGlobalChatEnabled(boolean enabled) {
        this.globalChatEnabled = enabled;
    }

    public int getPing() {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return -1;

        try {
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            return (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}