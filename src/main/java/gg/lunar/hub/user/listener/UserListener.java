package gg.lunar.hub.user.listener;

import gg.lunar.hub.LunarHub;
import gg.lunar.hub.feature.buildmode.BuildMode;
import gg.lunar.hub.feature.playervisibility.manager.PlayerVisibilityManager;
import gg.lunar.hub.selector.ServerSelectorMenu;
import gg.lunar.hub.spawn.SpawnManager;
import gg.lunar.hub.user.User;
import gg.lunar.hub.user.manager.UserManager;
import gg.lunar.hub.util.CC;
import gg.lunar.hub.util.hotbar.Items;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.UUID;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class UserListener implements Listener {

    private final UserManager userManager;
    private final PlayerVisibilityManager visibilityManager;
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();

    public UserListener(UserManager userManager, PlayerVisibilityManager visibilityManager) {
        this.userManager = userManager;
        this.visibilityManager = visibilityManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = userManager.createUser(player.getUniqueId(), player.getName());

        PlayerInventory inventory = player.getInventory();
        inventory.clear();

        if (Items.ENDER_BUTT.isEnabled()) {
            inventory.setItem(0, Items.ENDER_BUTT.toItemStack());
        }

        if (Items.SERVER_SELECTOR.isEnabled()) {
            inventory.setItem(4, Items.SERVER_SELECTOR.toItemStack());
        }

        boolean isHiding = visibilityManager.isHidingPlayers(player);
        if (Items.HIDE_PLAYERS.isEnabled() && Items.SHOW_PLAYERS.isEnabled()) {
            inventory.setItem(8, isHiding ? Items.SHOW_PLAYERS.toItemStack() : Items.HIDE_PLAYERS.toItemStack());
        }

        SpawnManager spawnManager = LunarHub.getInstance().getSpawnManager();
        if (spawnManager.isSpawnEnabled() && spawnManager.isSpawnOnJoinEnabled()) {
            spawnManager.teleportToSpawn(player);
        }

        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setSaturation(10.0f);

        visibilityManager.updateOnJoin(player);

        String joinMessage = LunarHub.get().getConfig().getString("JOIN_MESSAGE.ENABLED.MESSAGE", "&7[&a+&7] &a%player% &7has joined the server!");
        joinMessage = joinMessage.replace("%player%", player.getName());

        Bukkit.broadcastMessage(CC.translate(joinMessage));
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        SpawnManager spawnManager = LunarHub.getInstance().getSpawnManager();
        if (spawnManager.isSpawnEnabled()) {
            event.setRespawnLocation(spawnManager.getSpawnLocation());
        }
    }



    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            String serverSelectorTitle = new ServerSelectorMenu().getTitle(player);

            if (!BuildMode.isInBuildMode(player) || event.getView().getTitle().equals(serverSelectorTitle)) { ///  No im not retarded, I know I am checking the server selector title, its to see if they are in the server selector menu just to prevent the cancelled event from interfering with the server selector
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (!BuildMode.isInBuildMode(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
            ((Player) event.getEntity()).setFoodLevel(20);
            ((Player) event.getEntity()).setSaturation(10.0f);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        userManager.saveUser(player.getUniqueId());
        userManager.removeUser(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!event.getAction().name().contains("RIGHT_CLICK")) return;

        ItemStack item = player.getInventory().getItemInHand();
        if (item == null) return;

        if (item.isSimilar(Items.HIDE_PLAYERS.toItemStack()) || item.isSimilar(Items.SHOW_PLAYERS.toItemStack())) {
            event.setCancelled(true);

            UUID uuid = player.getUniqueId();
            long currentTime = System.currentTimeMillis();

            if (cooldowns.containsKey(uuid)) {
                long timeLeft = 3000 - (currentTime - cooldowns.get(uuid));
                if (timeLeft > 0) {
                    player.sendMessage(CC.translate("&cYou need to wait " + (timeLeft / 1000.0) + "s."));
                    return;
                }
            }
            cooldowns.put(uuid, currentTime);

            boolean isCurrentlyHiding = visibilityManager.isHidingPlayers(player);
            boolean newState = !isCurrentlyHiding;

            visibilityManager.setHidingPlayers(player, newState);

            player.getInventory().setItem(8, newState ? Items.SHOW_PLAYERS.toItemStack() : Items.HIDE_PLAYERS.toItemStack());
            player.sendMessage(CC.translate(newState ? "&b&lHub &7┃ &fYou are now &chiding &fall players." : "&bHub &7┃ &fYou are now &bshowing &fall players."));

            userManager.saveUser(uuid);
            return;
        }

        if (item.isSimilar(Items.SERVER_SELECTOR.toItemStack())) {
            event.setCancelled(true);
            new ServerSelectorMenu().openMenu(player);
        }
    }
}