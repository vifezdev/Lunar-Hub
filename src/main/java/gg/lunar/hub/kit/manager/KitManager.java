package gg.lunar.hub.kit.manager;

import gg.lunar.hub.LunarHub;
import gg.lunar.hub.config.ConfigFile;
import gg.lunar.hub.kit.Kit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class KitManager {
    private final ConfigFile kitsFile;
    private final Map<String, Kit> kits = new HashMap<>();

    public KitManager() {
        this.kitsFile = new ConfigFile(LunarHub.getInstance(), "kits.yml");
        loadKits();
    }

    private void loadKits() {
        FileConfiguration config = kitsFile.getConfig();
        ConfigurationSection section = config.getConfigurationSection("kits");

        if (section != null) {
            for (String key : section.getKeys(false)) {
                kits.put(key, Kit.fromConfig(section.getConfigurationSection(key), key));
            }
        }
    }

    public void saveKits() {
        FileConfiguration config = kitsFile.getConfig();
        config.set("kits", null);

        for (Kit kit : kits.values()) {
            kit.saveToConfig(config);
        }

        kitsFile.save();
    }

    public void createKit(String name) {
        if (!kits.containsKey(name)) {
            kits.put(name, new Kit(name, new ItemStack[36], new ItemStack[4]));
            saveKits();
        }
    }

    public void deleteKit(String name) {
        if (kits.containsKey(name)) {
            kits.remove(name);
            kitsFile.getConfig().set("kits." + name, null);
            kitsFile.save();
        }
    }

    public void setLoadout(Player player, String kitName) {
        Kit kit = kits.get(kitName);
        if (kit != null) {
            kit.setInventoryContents(player.getInventory().getContents());
            kit.setArmorContents(player.getInventory().getArmorContents());
            saveKits();
        }
    }

    public boolean kitExists(String name) {
        return kits.containsKey(name);
    }

    public boolean isKitComplete(String name) {
        Kit kit = kits.get(name);
        return kit != null && kit.isComplete();
    }

    public Map<String, Kit> getKits() {
        return kits;
    }
}