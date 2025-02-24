package gg.lunar.hub.scoreboard;

import gg.lunar.hub.LunarHub;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

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

        if(plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            lines = PlaceholderAPI.setPlaceholders(player, lines);
        }

        int onlineCount = plugin.getServer().getOnlinePlayers().size();
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, lines.get(i).replace("%server_online%", String.valueOf(onlineCount)));
        }

        return lines;
    }
}
