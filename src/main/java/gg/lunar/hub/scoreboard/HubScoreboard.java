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
    private boolean animationEnabled;
    private int animationInterval;
    private int animationIndex = 0;

    public void startAnimation() {
        this.animatedTitles = plugin.getScoreboardFile().getStringList("TITLE_ANIMATION.ANIMATIONS");
        this.animationEnabled = plugin.getScoreboardFile().getBoolean("TITLE_ANIMATION.ENABLED", false);
        this.animationInterval = plugin.getScoreboardFile().getInt("TITLE_ANIMATION.INTERVAL", 20);

        if (animationEnabled && !animatedTitles.isEmpty()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    animationIndex = (animationIndex + 1) % animatedTitles.size();
                }
            }.runTaskTimerAsynchronously(plugin, 0, animationInterval);
        }
    }

    @Override
    public String getTitle(Player player) {
        if (animationEnabled && !animatedTitles.isEmpty()) {
            return animatedTitles.get(animationIndex);
        }
        return plugin.getScoreboardFile().getString("title");
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
                    .replace("{ANIMATED_TITLE}", getTitle(player));

            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                line = PlaceholderAPI.setPlaceholders(player, line);
            }

            formattedLines.add(line);
        }

        return formattedLines;
    }
}