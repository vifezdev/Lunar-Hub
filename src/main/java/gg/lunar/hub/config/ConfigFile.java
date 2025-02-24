package gg.lunar.hub.config;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

@Getter
public class ConfigFile {

    private final File file;
    private FileConfiguration configuration;

    public ConfigFile(JavaPlugin plugin, String fileName) {
        this.file = new File(plugin.getDataFolder(), fileName);

        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            if (plugin.getResource(fileName) == null) {
                try {
                    this.file.createNewFile();
                } catch (IOException e) {
                    plugin.getLogger().severe("Failed to create new file " + fileName);
                }
            } else {
                plugin.saveResource(fileName, false);
            }
        }

        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public FileConfiguration getConfig() {
        return this.configuration;
    }

    public String getString(String path) {
        if (configuration.contains(path)) {
            return ChatColor.translateAlternateColorCodes('&', configuration.getString(path));
        }
        return null;
    }

    public List<String> getStringList(String path) {
        if (configuration.contains(path)) {
            ArrayList<String> strings = new ArrayList<>();
            for (String string : configuration.getStringList(path)) {
                strings.add(ChatColor.translateAlternateColorCodes('&', string));
            }
            return strings;
        }
        return Arrays.asList("ERROR: STRING LIST NOT FOUND!");
    }

    public void save() {
        try {
            this.configuration.save(this.file);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save config file " + this.file.toString());
            e.printStackTrace();
        }
    }

    public void reload() {
        if (!file.exists()) {
            Bukkit.getLogger().severe("Config file " + file.getName() + " does not exist. Creating a new one...");
            try {
                file.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().severe("Failed to create " + file.getName());
                e.printStackTrace();
                return;
            }
        }
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }
}