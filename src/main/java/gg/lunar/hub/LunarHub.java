package gg.lunar.hub;

import co.aikar.commands.BukkitCommandManager;
import gg.lunar.hub.command.MainCommand;
import gg.lunar.hub.config.ConfigFile;
import gg.lunar.hub.feature.buildmode.command.BuildmodeCommand;
import gg.lunar.hub.feature.doublejump.DoubleJump;
import gg.lunar.hub.feature.enderbutt.Enderbutt;
import gg.lunar.hub.feature.playervisibility.manager.PlayerVisibilityManager;
import gg.lunar.hub.hologram.HologramsCommand;
import gg.lunar.hub.kit.commands.KitCommands;
import gg.lunar.hub.scoreboard.HubScoreboard;
import gg.lunar.hub.selector.listener.SelectorListener;
import gg.lunar.hub.spawn.SpawnManager;
import gg.lunar.hub.user.listener.UserListener;
import gg.lunar.hub.user.manager.UserManager;
import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public final class LunarHub extends JavaPlugin {

    private static LunarHub instance;

    private ConfigFile scoreboardFile;
    private ConfigFile hotbarFile;
    private ConfigFile usersFile;
    private UserManager userManager;
    private Assemble scoreboard;
    private BukkitCommandManager commandManager;
    private PlayerVisibilityManager playerVisibilityManager;
    private SpawnManager spawnManager;

    @Override
    public void onEnable() {
        instance = this;
        loadFiles();

        this.userManager = new UserManager();
        this.playerVisibilityManager = new PlayerVisibilityManager();
        this.spawnManager = new SpawnManager(this);

        setupScoreboard();
        registerListener();
        registerCommands();
    }

    private void registerCommands() {
        this.commandManager = new BukkitCommandManager(this);
        this.commandManager.registerCommand(new BuildmodeCommand());
        this.commandManager.registerCommand(new HologramsCommand(this));
        this.commandManager.registerCommand(new MainCommand());
        this.commandManager.registerCommand(new KitCommands());
    }

    private void loadFiles() {
        saveDefaultConfig();
        this.scoreboardFile = new ConfigFile(this, "scoreboard.yml");
        this.hotbarFile = new ConfigFile(this, "hotbar.yml");
        this.usersFile = new ConfigFile(this, "data/users.yml");
    }

    private void setupScoreboard() {
        this.scoreboard = new Assemble(this, new HubScoreboard(this));
        this.scoreboard.setAssembleStyle(AssembleStyle.MODERN);
        this.scoreboard.setTicks(2);
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(new UserListener(userManager, playerVisibilityManager), this);
        getServer().getPluginManager().registerEvents(new DoubleJump(this), this);
        getServer().getPluginManager().registerEvents(new Enderbutt(), this);
        getServer().getPluginManager().registerEvents(new SelectorListener(), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onDisable() {
        if (scoreboard != null) {
            scoreboard.cleanup();
        }
    }

    public PlayerVisibilityManager getPlayerVisibilityManager() {
        return playerVisibilityManager;
    }

    public static LunarHub get() {
        return getPlugin(LunarHub.class);
    }

    public SpawnManager getSpawnManager() {
        return spawnManager;
    }

    public static LunarHub getInstance() {
        return instance;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public ConfigFile getUsersFile() {
        return usersFile;
    }

    public ConfigFile getHotbarFile() {
        return hotbarFile;
    }

    public ConfigFile getScoreboardFile() {
        return scoreboardFile;
    }
}