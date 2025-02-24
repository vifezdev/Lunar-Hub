package gg.lunar.hub.feature.pvpmode.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import gg.lunar.hub.feature.pvpmode.PvPMode;
import gg.lunar.hub.util.CC;
import org.bukkit.entity.Player;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

@CommandAlias("pvpmode")
@Description("Manage your PvP Mode state.")
public class PvPModeCommand extends BaseCommand {

    @Default
    @CatchUnknown
    public void onDefault(Player player) {
        player.sendMessage("");
        player.sendMessage(CC.translate("&b&lLunarGG PvPMode"));
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&7* &b/pvpmode &cexit &7- &fDisable PvP Mode"));
        player.sendMessage(" ");
    }

    @Subcommand("exit|disable")
    @Description("Exit PvP Mode.")
    public void onExit(Player player) {
        if (!PvPMode.isInPvPMode(player)) {
            player.sendMessage(CC.translate("&cYou are not in PvP Mode!"));
            return;
        }

        PvPMode.disable(player);
        player.sendMessage(CC.translate("&aYou have exited PvP Mode."));
    }
}