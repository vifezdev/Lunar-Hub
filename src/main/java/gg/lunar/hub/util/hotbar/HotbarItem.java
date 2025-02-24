package gg.lunar.hub.util.hotbar;

import gg.lunar.hub.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class HotbarItem {

    private final boolean enabled;
    private final int slot;
    private final String displayName;
    private final Material material;
    private final List<String> lore;

    public HotbarItem(ConfigurationSection section, Material defaultMaterial, String defaultName, List<String> defaultLore, int defaultSlot) {
        if (section == null) {
            throw new IllegalArgumentException("HotbarItem configuration section is null! Check your config.");
        }

        this.enabled = section.getBoolean("ENABLED", true);
        this.slot = section.getInt("SLOT", defaultSlot);
        this.material = Material.matchMaterial(section.getString("MATERIAL", defaultMaterial.name()));
        this.displayName = ChatColor.translateAlternateColorCodes('&', section.getString("DISPLAY_NAME", defaultName));
        this.lore = section.getStringList("LORE").isEmpty() ? defaultLore :
                section.getStringList("LORE").stream().map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList());
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack toItemStack() {
        return new ItemBuilder(material).setName(displayName).setLore(lore).build();
    }
}