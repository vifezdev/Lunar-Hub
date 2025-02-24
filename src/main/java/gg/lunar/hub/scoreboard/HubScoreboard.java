package gg.lunar.hub.scoreboard;

import gg.lunar.hub.LunarHub;
import gg.lunar.hub.user.User;
import gg.lunar.hub.user.manager.UserManager;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

@RequiredArgsConstructor
public class HubScoreboard implements AssembleAdapter {

    private final LunarHub plugin;

    @Override
    public String getTitle(Player player) {
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
                    .replace("%player_ping%", String.valueOf(ping));

            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                line = PlaceholderAPI.setPlaceholders(player, line);
            }

            formattedLines.add(line);
        }

        return formattedLines;
    }
}