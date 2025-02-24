package gg.lunar.hub.feature.doublejump;

import gg.lunar.hub.LunarHub;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.Set;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class DoubleJump implements Listener {

    private final LunarHub plugin;
    private final Set<Player> canDoubleJump = new HashSet<>();

    public DoubleJump(LunarHub plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = plugin.getConfig();

        if (!config.getBoolean("DOUBLE_JUMP_CONFIG.ENABLED")) return;
        
        event.setCancelled(true);

        player.setFlying(false);

        player.setAllowFlight(false);


        double height = config.getDouble("DOUBLE_JUMP_CONFIG.HEIGHT_VALUE", 1.0);

        double lengthMultiplier = config.getDouble("DOUBLE_JUMP_CONFIG.LENGTH_MULTIPLIER", 1.5);

        boolean shiftBoostEnabled = config.getBoolean("DOUBLE_JUMP_CONFIG.SHIFT_BOOST_ENABLED", true);

        double shiftMultiplier = config.getDouble("DOUBLE_JUMP_CONFIG.SHIFT_MULTIPLIER", 3.0);


        Vector velocity = player.getLocation().getDirection().multiply(lengthMultiplier).setY(height);

        if (shiftBoostEnabled && player.isSneaking()) velocity.multiply(shiftMultiplier);

        player.setVelocity(velocity);
        player.playSound(player.getLocation(), Sound.valueOf("BAT_TAKEOFF"), 1.0f, 1.5f);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        if (player.isOnGround() && !canDoubleJump.contains(player)) {
            canDoubleJump.add(player);

            player.setAllowFlight(true);

        } else if (!player.isOnGround() && canDoubleJump.contains(player)) {

            canDoubleJump.remove(player);
        }
    }
}