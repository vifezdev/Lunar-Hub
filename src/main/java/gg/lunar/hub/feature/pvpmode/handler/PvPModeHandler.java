package gg.lunar.hub.feature.pvpmode.handler;

import gg.lunar.hub.LunarHub;
import gg.lunar.hub.feature.pvpmode.PvPMode;
import gg.lunar.hub.kit.manager.KitManager;
import gg.lunar.hub.util.CC;
import gg.lunar.hub.util.hotbar.Items;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * Copyright (c) 2025 Vifez. All rights reserved.
 * Unauthorized use or distribution is prohibited.
 */

public class PvPModeHandler {

    private static final Map<UUID, BukkitRunnable> activationTasks = new HashMap<>();

    public static void startActivation(final Player player) {
        UUID uuid = player.getUniqueId();

        if (PvPMode.isInPvPMode(player)) {
            player.sendMessage(CC.translate("&cYou are already in PvP Mode!"));
            return;
        }

        if (activationTasks.containsKey(uuid)) {
            activationTasks.get(uuid).cancel();
            activationTasks.remove(uuid);
        }

        BukkitRunnable task = new BukkitRunnable() {
            int countdown = 3;

            @Override
            public void run() {
                if (!player.isOnline() || !player.isValid()) {
                    activationTasks.remove(uuid);
                    cancel();
                    return;
                }

                if (!Items.isHoldingPvPModeItem(player)) {
                    player.sendMessage(CC.translate("&cPvP Mode activation cancelled!"));
                    activationTasks.remove(uuid);
                    cancel();
                    return;
                }

                if (countdown > 0) {
                    player.sendMessage(CC.translate("&cPvP Mode &7┃ &fEnabling in &b" + countdown + "s&f!"));
                    countdown--;
                } else {
                    enablePvPMode(player);
                    activationTasks.remove(uuid);
                    cancel();
                }
            }
        };

        task.runTaskTimer(LunarHub.getInstance(), 0L, 20L);
        activationTasks.put(uuid, task);
    }

    private static void enablePvPMode(Player player) {
        PvPMode.enable(player);
        player.sendMessage(CC.translate("&aPvP Mode enabled!"));

        sendActionBar(player, "&cPvP Mode &7┃ &bPvP Mode is now &aenabled!");

        Bukkit.getScheduler().runTask(LunarHub.getInstance(), () -> KitManager.giveKit(player, "Warrior"));
    }

    private static void sendActionBar(Player player, String message) {
        try {
            Object chatComponent = getNMSClass("ChatComponentText")
                    .getConstructor(String.class)
                    .newInstance(CC.translate(message));

            Object packet = getNMSClass("PacketPlayOutChat")
                    .getConstructor(getNMSClass("IChatBaseComponent"), byte.class)
                    .newInstance(chatComponent, (byte) 2);

            Object playerHandle = Class.forName("org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer")
                    .getMethod("getHandle")
                    .invoke(player);

            Object playerConnection = playerHandle.getClass()
                    .getField("playerConnection")
                    .get(playerHandle);

            playerConnection.getClass()
                    .getMethod("sendPacket", getNMSClass("Packet"))
                    .invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Class<?> getNMSClass(String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server.v1_8_R3." + name);
    }
}