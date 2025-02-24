package gg.lunar.hub.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import gg.lunar.hub.LunarHub;
import gg.lunar.hub.scoreboard.HubScoreboard;
import gg.lunar.hub.util.CC;
import org.bukkit.entity.Player;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

@CommandAlias("hub|lh|lunarhub")
public class MainCommand extends BaseCommand {

    @Default
    @CommandPermission("hub.command")
    public void onDefaultCommand(Player player) {
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&b&lLunarGG Hub"));
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&7* &fAuthor&7: &bvifez"));
        player.sendMessage(CC.translate("&7* &fVersion&7: &bv" + LunarHub.getInstance().getDescription().getVersion()));
        player.sendMessage(CC.translate("&7* &fWebsite&7: &bhttps://lunar.gg"));
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&bUseful commands"));
        player.sendMessage(CC.translate("&7* &b/hub reload &7- &fReload all configuration files"));
        player.sendMessage(CC.translate("&7* &b/hub setspawn &7- &fSet the spawn location"));
        player.sendMessage(" ");
    }

    @Subcommand("reload")
    @CommandPermission("hub.reload")
    public void onReload(Player player) {
        LunarHub.getInstance().reloadConfig();
        LunarHub.getInstance().getHotbarFile().reload();
        LunarHub.getInstance().getScoreboardFile().reload();
        LunarHub.getInstance().getUsersFile().reload();
        LunarHub.getInstance().getKitsFile().reload();

        if (LunarHub.getInstance().getScoreboard().getAdapter() instanceof HubScoreboard) {
            HubScoreboard hubScoreboard = (HubScoreboard) LunarHub.getInstance().getScoreboard().getAdapter();
            hubScoreboard.startAnimation();
        }

        player.sendMessage(CC.translate("&b&lHub &7┃ &fConfigurations reloaded &bsuccessfully"));
    }

    @Subcommand("setspawn")
    @CommandPermission("hub.setspawn")
    public void onSetSpawn(Player player) {
        LunarHub.getInstance().getSpawnManager().setSpawn(player.getLocation());
        player.sendMessage(CC.translate("&b&lHub &7┃ &fSpawn location set successfully!"));
    }
}