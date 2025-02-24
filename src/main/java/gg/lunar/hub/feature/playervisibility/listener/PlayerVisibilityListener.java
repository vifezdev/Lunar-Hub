package gg.lunar.hub.feature.playervisibility.listener;

import gg.lunar.hub.feature.playervisibility.manager.PlayerVisibilityManager;
import gg.lunar.hub.util.hotbar.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class PlayerVisibilityListener implements Listener {
    private final PlayerVisibilityManager visibilityManager;

    public PlayerVisibilityListener(PlayerVisibilityManager visibilityManager) {
        this.visibilityManager = visibilityManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (item == null || item.getType() != Material.INK_SACK) {
            return;
        }

        byte data = item.getData().getData();
        if (data != 8 && data != 10) {
            return;
        }

        boolean isCurrentlyHiding = visibilityManager.isHidingPlayers(player);
        boolean newState = !isCurrentlyHiding;

        visibilityManager.setHidingPlayers(player, newState);
    }
}