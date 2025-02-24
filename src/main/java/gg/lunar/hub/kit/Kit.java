package gg.lunar.hub.kit;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Kit {
    private final String name;
    private ItemStack[] inventoryContents;
    private ItemStack[] armorContents;

    public Kit(String name, ItemStack[] inventoryContents, ItemStack[] armorContents) {
        this.name = name;
        this.inventoryContents = inventoryContents;
        this.armorContents = armorContents;
    }

    public String getName() {
        return name;
    }

    public ItemStack[] getInventoryContents() {
        return inventoryContents;
    }

    public ItemStack[] getArmorContents() {
        return armorContents;
    }

    public void setInventoryContents(ItemStack[] inventoryContents) {
        this.inventoryContents = inventoryContents;
    }

    public void setArmorContents(ItemStack[] armorContents) {
        this.armorContents = armorContents;
    }

    public boolean isComplete() {
        return inventoryContents != null && armorContents != null;
    }

    public void saveToConfig(FileConfiguration config) {
        String path = "kits." + name;
        List<String> inventoryData = new ArrayList<>();
        List<String> armorData = new ArrayList<>();

        for (ItemStack item : inventoryContents) {
            if (item != null) {
                inventoryData.add(item.getType().name() + ":" + item.getAmount());
            }
        }

        for (ItemStack item : armorContents) {
            if (item != null) {
                armorData.add(item.getType().name() + ":" + item.getAmount());
            }
        }

        config.set(path + ".inventory", inventoryData);
        config.set(path + ".armor", armorData);
    }

    public static Kit fromConfig(ConfigurationSection section, String name) {
        List<String> inventoryList = section.getStringList("inventory");
        List<String> armorList = section.getStringList("armor");

        ItemStack[] inventory = new ItemStack[36];
        ItemStack[] armor = new ItemStack[4];

        int i = 0;
        for (String itemString : inventoryList) {
            String[] parts = itemString.split(":");
            try {
                inventory[i] = new ItemStack(org.bukkit.Material.valueOf(parts[0]), Integer.parseInt(parts[1]));
            } catch (Exception e) {
                inventory[i] = null;
            }
            i++;
        }

        i = 0;
        for (String itemString : armorList) {
            String[] parts = itemString.split(":");
            try {
                armor[i] = new ItemStack(org.bukkit.Material.valueOf(parts[0]), Integer.parseInt(parts[1]));
            } catch (Exception e) {
                armor[i] = null;
            }
            i++;
        }

        return new Kit(name, inventory, armor);
    }
}