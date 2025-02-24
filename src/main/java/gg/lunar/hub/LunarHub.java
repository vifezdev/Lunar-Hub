package gg.lunar.hub;

import gg.lunar.hub.config.ConfigFile;
import gg.lunar.hub.user.manager.UserManager;
import gg.lunar.hub.user.listener.UserListener;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public final class LunarHub extends JavaPlugin {

    private ConfigFile scoreboardFile;
    private UserManager userManager;

    @Override
    public void onEnable() {
        this.userManager = new UserManager();

        loadFiles();
        registerListener();
    }

    private void loadFiles() {
        saveDefaultConfig();
        this.scoreboardFile = new ConfigFile(this, "scoreboard.yml");
    }

    public ConfigFile getScoreboardFile() {
        return scoreboardFile;
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(new UserListener(userManager), this);
    }

    @Override
    public void onDisable() {
    }

    public UserManager getUserManager() {
        return userManager;
    }
}