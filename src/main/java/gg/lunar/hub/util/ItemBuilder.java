package gg.lunar.hub.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class ItemBuilder {
    private final ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setLore(lore.stream()
                    .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                    .collect(Collectors.toList()));
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}