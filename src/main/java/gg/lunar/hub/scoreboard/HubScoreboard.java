package gg.lunar.hub.scoreboard;

import gg.lunar.hub.LunarHub;
import gg.lunar.hub.user.User;
import gg.lunar.hub.user.manager.UserManager;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

@RequiredArgsConstructor
public class HubScoreboard implements AssembleAdapter {

    private final LunarHub plugin;
    private List<String> animatedTitles;
    private List<String> animatedFooters;
    private boolean titleAnimationEnabled;
    private boolean footerAnimationEnabled;
    private int titleAnimationInterval;
    private int footerAnimationInterval;
    private int titleAnimationIndex = 0;
    private int footerAnimationIndex = 0;

    public void startAnimation() {
        // Load title animation settings
        this.animatedTitles = plugin.getScoreboardFile().getStringList("TITLE_ANIMATION.ANIMATIONS");
        this.titleAnimationEnabled = plugin.getScoreboardFile().getBoolean("TITLE_ANIMATION.ENABLED", false);
        this.titleAnimationInterval = plugin.getScoreboardFile().getInt("TITLE_ANIMATION.INTERVAL", 20);

        // Load footer animation settings
        this.animatedFooters = plugin.getScoreboardFile().getStringList("FOOTER_ANIMATION.ANIMATIONS");
        this.footerAnimationEnabled = plugin.getScoreboardFile().getBoolean("FOOTER_ANIMATION.ENABLED", false);
        this.footerAnimationInterval = plugin.getScoreboardFile().getInt("FOOTER_ANIMATION.INTERVAL", 20);

        // Start title animation
        if (titleAnimationEnabled && !animatedTitles.isEmpty()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    titleAnimationIndex = (titleAnimationIndex + 1) % animatedTitles.size();
                }
            }.runTaskTimerAsynchronously(plugin, 0, titleAnimationInterval);
        }

        // Start footer animation
        if (footerAnimationEnabled && !animatedFooters.isEmpty()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    footerAnimationIndex = (footerAnimationIndex + 1) % animatedFooters.size();
                }
            }.runTaskTimerAsynchronously(plugin, 0, footerAnimationInterval);
        }
    }

    @Override
    public String getTitle(Player player) {
        if (titleAnimationEnabled && !animatedTitles.isEmpty()) {
            return animatedTitles.get(titleAnimationIndex);
        }
        return plugin.getScoreboardFile().getString("title");
    }

    public String getFooter() {
        if (footerAnimationEnabled && !animatedFooters.isEmpty()) {
            return animatedFooters.get(footerAnimationIndex);
        }
        return "";
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = plugin.getScoreboardFile().getStringList("lines");
        List<String> formattedLines = new ArrayList<>();

        int onlineCount = Bukkit.getOnlinePlayers().size();
        UserManager userManager = plugin.getUserManager();
        User user = userManager.getUser(player.getUniqueId());

        int ping = (user != null) ? user.getPing() : -1;
        String playerName = player.getName();

        for (String line : lines) {
            line = line.replace("%server_online%", String.valueOf(onlineCount))
                    .replace("%player_name%", playerName)
                    .replace("%player_ping%", String.valueOf(ping))
                    .replace("{ANIMATED_TITLE}", getTitle(player))
                    .replace("{ANIMATED_FOOTER}", getFooter());

            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                line = PlaceholderAPI.setPlaceholders(player, line);
            }

            formattedLines.add(line);
        }

        return formattedLines;
    }
}