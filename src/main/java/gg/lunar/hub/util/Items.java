package gg.lunar.hub.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public enum Items {

    SERVER_SELECTOR("&bServer Selector &7(Right-click)", Material.COMPASS,
            "&7Right-click to open the server selector!");

    private final String displayName;
    private final Material material;
    private final List<String> lore;

    Items(String displayName, Material material, String... lore) {
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        this.material = material;
        this.lore = Arrays.asList(lore);
    }

    public ItemStack toItemStack() {
        return new ItemBuilder(material)
                .setName(displayName)
                .setLore(lore)
                .build();
    }
}