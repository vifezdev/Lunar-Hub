package gg.lunar.hub.user.listener;

import gg.lunar.hub.user.manager.UserManager;
import gg.lunar.hub.util.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.PlayerInventory;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class UserListener implements Listener {

    private final UserManager userManager;

    public UserListener(UserManager userManager) {
        this.userManager = userManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        userManager.createUser(player.getUniqueId(), player.getName());

        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        inventory.setItem(0, Items.SERVER_SELECTOR.toItemStack());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        userManager.removeUser(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }
}