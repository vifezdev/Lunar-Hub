package gg.lunar.hub.feature.buildmode.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import gg.lunar.hub.feature.buildmode.BuildMode;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

@CommandAlias("buildmode|bm")
public class BuildmodeCommand extends BaseCommand {

    @Default
    public void onBuildModeToggle(Player player) {
        if (BuildMode.isInBuildMode(player)) {
            BuildMode.disable(player);
            player.setGameMode(GameMode.ADVENTURE);
            player.sendMessage("§cBuild mode disabled.");
        } else {
            BuildMode.enable(player);
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage("§aBuild mode enabled.");
        }
    }
}