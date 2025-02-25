package gg.lunar.hub.util.hotbar;

import gg.lunar.hub.LunarHub;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public enum Items {

    ENDER_BUTT("ENDER_BUTT", Material.ENDER_PEARL, "&bEnder Butt &7(Right-click)",
            Arrays.asList("&7Right-click to use the Ender Butt!"), 0),

    PVP_MODE("PVP_MODE", Material.DIAMOND_SWORD, "&bPvP Mode &7(Hold)",
            Arrays.asList("&7Hold for &b3 seconds &7to enable &cPvPMode!"), 1),

    SERVER_SELECTOR("SERVER_SELECTOR", Material.COMPASS, "&bServer Selector &7(Right-click)",
            Arrays.asList("&7Right-click to open the server selector!"), 4),

    SETTINGS("SETTINGS", Material.REDSTONE_COMPARATOR, "&bSettings &7(Right-click)",
            Arrays.asList("&7Right-click to open the &bSettings!"), 8),

    HIDE_PLAYERS("HIDE_PLAYERS", Material.INK_SACK, (short) 8, "&7Hide Players (Right-click)",
            Arrays.asList("&7Click to hide all players"), 7),

    SHOW_PLAYERS("SHOW_PLAYERS", Material.INK_SACK, (short) 10, "&aShow Players (Right-click)",
            Arrays.asList("&7Click to show all players"), 7);

    private HotbarItem hotbarItem;
    private final short durability;

    Items(String path, Material defaultMaterial, short durability, String defaultName, List<String> defaultLore, int defaultSlot) {
        this.durability = durability;

        ConfigurationSection section = LunarHub.getInstance().getHotbarFile().getConfig().getConfigurationSection("HOTBAR." + path);
        this.hotbarItem = new HotbarItem(section, defaultMaterial, defaultName, defaultLore, defaultSlot);
    }

    Items(String path, Material defaultMaterial, String defaultName, List<String> defaultLore, int defaultSlot) {
        this(path, defaultMaterial, (short) 0, defaultName, defaultLore, defaultSlot);
    }

    public boolean isEnabled() {
        return hotbarItem.isEnabled();
    }

    public ItemStack toItemStack() {
        ItemStack item = hotbarItem.toItemStack();
        item.setDurability(durability);
        return item;
    }

    public static void reloadItems() {
        for (Items item : values()) {
            ConfigurationSection section = LunarHub.getInstance().getHotbarFile().getConfig().getConfigurationSection("HOTBAR." + item.name());
            if (section != null) {
                item.hotbarItem = new HotbarItem(section, item.hotbarItem.toItemStack().getType(),
                        item.hotbarItem.toItemStack().getItemMeta().getDisplayName(),
                        item.hotbarItem.toItemStack().getItemMeta().getLore(),
                        item.hotbarItem.getSlot());
            }
        }
    }

    public static boolean isHoldingPvPModeItem(Player player) {
        ItemStack item = player.getItemInHand();
        return item != null && item.isSimilar(PVP_MODE.toItemStack());
    }
}