package gg.lunar.hub.kit.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import gg.lunar.hub.kit.Kit;
import gg.lunar.hub.kit.manager.KitManager;
import gg.lunar.hub.util.CC;
import org.bukkit.entity.Player;

@CommandAlias("kit")
@CommandPermission("kit.command")
public class KitCommands extends BaseCommand {
    private final KitManager kitManager;

    public KitCommands(KitManager kitManager) {
        this.kitManager = kitManager;
    }

    @Default
    public void onDefaultCommand(Player player) {
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&b&lLunarGG Kits"));
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&7* &b/kit create &7<kitName> - &fCreate a kit"));
        player.sendMessage(CC.translate("&7* &b/kit delete &7<kitName> - &fDelete a kit"));
        player.sendMessage(CC.translate("&7* &b/kit setLoadout &7<kitName> - &fSets a kit's inventory items and armor"));
        player.sendMessage(CC.translate("&7* &b/kit getLoadout &7<kitName> - &fSets your inventory to the kit's items"));
        player.sendMessage(CC.translate("&7* &b/kit status &7<kitName> - &fCheck a kit's status"));
        player.sendMessage(CC.translate("&7* &b/kit list - &fLists all kits"));
        player.sendMessage(" ");
    }

    @Subcommand("create")
    public void onCreate(Player player, String kitName) {
        if (kitManager.kitExists(kitName)) {
            player.sendMessage(CC.translate("&cA kit with that name already exists!"));
            return;
        }
        kitManager.createKit(kitName);
        player.sendMessage(CC.translate("&aKit &b" + kitName + " &acreated successfully!"));
    }

    @Subcommand("delete")
    public void onDelete(Player player, String kitName) {
        if (!kitManager.kitExists(kitName)) {
            player.sendMessage(CC.translate("&cKit not found!"));
            return;
        }
        kitManager.deleteKit(kitName);
        player.sendMessage(CC.translate("&aKit &b" + kitName + " &adeleted successfully!"));
    }

    @Subcommand("setLoadout")
    public void onSetLoadout(Player player, String kitName) {
        if (!kitManager.kitExists(kitName)) {
            player.sendMessage(CC.translate("&cKit not found!"));
            return;
        }
        kitManager.setLoadout(player, kitName);
        player.sendMessage(CC.translate("&aKit &b" + kitName + " &aloadout saved!"));
    }

    @Subcommand("getLoadout")
    public void onGetLoadout(Player player, String kitName) {
        if (!kitManager.kitExists(kitName)) {
            player.sendMessage(CC.translate("&cKit not found!"));
            return;
        }
        Kit kit = kitManager.getKits().get(kitName);
        if (kit == null || !kit.isComplete()) {
            player.sendMessage(CC.translate("&cKit is incomplete or not found!"));
            return;
        }

        player.getInventory().setContents(kit.getInventoryContents());
        player.getInventory().setArmorContents(kit.getArmorContents());
        player.sendMessage(CC.translate("&aYou have received the &b" + kitName + " &akit!"));
    }

    @Subcommand("status")
    public void onStatus(Player player, String kitName) {
        if (!kitManager.kitExists(kitName)) {
            player.sendMessage(CC.translate("&cKit not found!"));
            return;
        }
        boolean complete = kitManager.isKitComplete(kitName);
        player.sendMessage(CC.translate("&bKit " + kitName + " status: " + (complete ? "&aComplete" : "&cIncomplete")));
    }

    @Subcommand("list")
    public void onList(Player player) {
        if (kitManager.getKits().isEmpty()) {
            player.sendMessage(CC.translate("&cNo kits found!"));
            return;
        }
        player.sendMessage(CC.translate("&b&lAvailable Kits:"));
        for (String kit : kitManager.getKits().keySet()) {
            player.sendMessage(CC.translate("&7- &b" + kit));
        }
    }
}