package gg.lunar.hub.selector;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.common.io.ByteArrayDataInput;
import gg.lunar.hub.LunarHub;
import gg.lunar.hub.util.ItemBuilder;
import gg.lunar.hub.util.menu.Button;
import gg.lunar.hub.util.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.*;

public class ServerSelectorMenu extends Menu implements PluginMessageListener {

    private static final Map<String, Integer> playerCounts = new HashMap<>();
    private static final List<String> servers = Arrays.asList("skyblock", "SoupPvP", "HCTeams");

    public ServerSelectorMenu() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(LunarHub.get(), "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(LunarHub.get(), "BungeeCord", this);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    for (String server : servers) {
                        requestPlayerCount(player, server);
                    }
                }
            }
        }.runTaskTimerAsynchronously(LunarHub.get(), 0L, 40L);
    }

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

        buttons.put(11, createServerButton("skyblock", "&b&lPractice", new String[]{
                " ",
                " &7* &bSeason X &fstarted &bJune 25th, 2022",
                " &7* &bMulti&7-&bGamemode Arena PvP",
                " &7* &bServers all around the World",
                " ",
                "&eJoin queue!"
        }, Material.DIAMOND_SWORD));

        buttons.put(13, createServerButton("SoupPvP", "&b&lSoupPvP", new String[]{
                " ",
                " &7* &bSeason 5 &fstarted &bMarch 4th, 2022",
                " &7* &bOpen PvP Arena!",
                " ",
                "&eJoin queue!"
        }, Material.BOWL));

        buttons.put(15, createServerButton("HCTeams", "&b&lHCTeams", new String[]{
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
                String playerCount = playerCounts.getOrDefault(serverName, -1) == -1 ? "?" : String.valueOf(playerCounts.get(serverName));

                List<String> newLore = new ArrayList<>();
                newLore.add(ChatColor.GRAY + playerCount + " Player(s)");
                newLore.addAll(Arrays.asList(lore));

                return new ItemBuilder(material)
                        .setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName))
                        .setLore(newLore)
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

    public static void requestPlayerCount(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);
        player.sendPluginMessage(LunarHub.get(), "BungeeCord", out.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        if (message.length == 0) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);

        try {
            if (message.length < 2) {
                return;
            }

            String subchannel = in.readUTF();

            if (subchannel.equals("PlayerCount")) {
                if (message.length < 6) {
                    return;
                }

                String server = in.readUTF();

                if (message.length >= 6 + server.length()) {
                    int playerCount = in.readInt();
                    playerCounts.put(server, playerCount);
                }
            }
        } catch (Exception ignored) {
        }
    }
}