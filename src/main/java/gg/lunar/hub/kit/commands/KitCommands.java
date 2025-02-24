package gg.lunar.hub.kit.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import gg.lunar.hub.util.CC;
import org.bukkit.entity.Player;

@CommandAlias("kit")
public class KitCommands extends BaseCommand {

    @Default
    @CommandPermission("kit.command")
    public void onDefaultCommand(Player player) {
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&b&lLunarGG Kits"));
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&7* &b/kit create &7<kitName> - &fCreate a kit"));
        player.sendMessage(CC.translate("&7* &b/kit delete &7<kitName> - &fDelete a kit"));
        player.sendMessage(CC.translate("&7* &b/kit setLoadout &7<kitName> - &fSets a kits inventory items and armor"));
        player.sendMessage(CC.translate("&7* &b/kit status &7<kitName> - &fCheck a kits status")); // Shows if a kit is fully setup or not, Like if the kits loadout has been set etc
        player.sendMessage(CC.translate("&7* &b/kit list - &fLists all kits"));
        player.sendMessage(" ");

    }
}