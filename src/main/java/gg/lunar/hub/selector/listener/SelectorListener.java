package gg.lunar.hub.selector.listener;

import gg.lunar.hub.selector.ServerSelectorMenu;
import gg.lunar.hub.util.menu.Button;
import gg.lunar.hub.util.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class SelectorListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        Menu menu = Menu.getOpenedMenus().get(player);

        if (menu == null) return;

        event.setCancelled(true);

        if (event.getClickedInventory() == null || event.getSlot() < 0) return;

        Button button = menu.getButtons(player).get(event.getSlot());
        if (button != null) {
            button.click(player, event.getSlot(), event.getClick(), event.getHotbarButton());

            if (menu instanceof ServerSelectorMenu) {
                String serverName = getServerNameFromSlot(event.getSlot());
                ServerSelectorMenu.sendToServer(player, serverName);
                player.closeInventory();
                player.sendMessage(ChatColor.GRAY + "Connecting to " + ChatColor.AQUA + serverName + ChatColor.GRAY + "...");
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (Menu.getOpenedMenus().containsKey(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            Menu.getOpenedMenus().remove(player);
        }
    }

    private String getServerNameFromSlot(int slot) {
        switch (slot) {
            case 11: return "skyblock";
            case 13: return "SoupPvP";
            case 15: return "HCTeams";
            default: return "Unknown";
        }
    }
}