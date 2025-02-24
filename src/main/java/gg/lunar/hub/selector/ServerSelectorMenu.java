package gg.lunar.hub.selector;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import gg.lunar.hub.LunarHub;
import gg.lunar.hub.util.ItemBuilder;
import gg.lunar.hub.util.menu.Button;
import gg.lunar.hub.util.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class ServerSelectorMenu extends Menu {

    @Override
    public String getTitle(Player player) {
        return ChatColor.AQUA + "Server Selector";
    }

    @Override
    public String getRawTitle(Player player) {
        return "&bServer Selector";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (int i = 0; i < 27; i++) {
            buttons.put(i, Button.createPlaceholder(Material.STAINED_GLASS_PANE, (short) 7));
        }

        buttons.put(11, createServerButton("skyblock", "&b&lPractice",
                new String[]{
                        "&70 Players",
                        " ",
                        " &7* &bSeason X &fstarted &bJune 25th, 2022",
                        " &7* &bMulti&7-&bGamemode Arena PvP",
                        " &7* &bServers all around the World",
                        " ",
                        "&eJoin queue!"
                }, Material.DIAMOND_SWORD));

        buttons.put(13, createServerButton("SoupPvP", "&b&lSoupPvP",
                new String[]{
                        "&70 Players",
                        " ",
                        " &7* &bSeason 5 &fstarted &bMarch 4th, 2022",
                        " &7* &bOpen PvP Arena!",
                        " ",
                        "&eJoin queue!"
                }, Material.BOWL));

        buttons.put(15, createServerButton("HCTeams", "&b&lHCTeams",
                new String[]{
                        "&70 Players",
                        " ",
                        " &7* &aLite &bMap 3 &fbegins &bMarch 4th, 2022",
                        " &7* &bOpen World Survival",
                        " &7* &bTracking and Raiding",
                        " &7* &6Gold &bEconomy",
                        " &7* &bKits: &aEnabled",
                        " ",
                        "&eJoin queue!"
                }, Material.GOLD_INGOT));

        return buttons;
    }

    private Button createServerButton(String serverName, String displayName, String[] lore, Material material) {
        return new Button() {
            @Override
            public ItemStack getItem(Player player) {
                return new ItemBuilder(material)
                        .setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName))
                        .setLore(Arrays.asList(lore))
                        .build();
            }
        };
    }

    public static void sendToServer(Player player, String server) {
        ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
        dataOutput.writeUTF("Connect");
        dataOutput.writeUTF(server);
        player.sendPluginMessage(LunarHub.get(), "BungeeCord", dataOutput.toByteArray());
    }
}