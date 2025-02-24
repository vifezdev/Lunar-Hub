package gg.lunar.hub.feature.enderbutt;

import gg.lunar.hub.util.hotbar.Items;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class Enderbutt implements Listener {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (item == null || !item.isSimilar(Items.ENDER_BUTT.toItemStack())) return;

        event.setCancelled(true);

        if (player.getGameMode() == GameMode.CREATIVE) return;

        UUID uuid = player.getUniqueId();
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(uuid) && (currentTime - cooldowns.get(uuid) < 3000)) {
            player.sendMessage("§cYou must wait before using the EnderButt again.");
            return;
        }
        cooldowns.put(uuid, currentTime);

        EnderPearl pearl = player.launchProjectile(EnderPearl.class);
        pearl.setVelocity(player.getLocation().getDirection().multiply(3.0));

        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("LunarHub"), () -> {
            pearl.setPassenger(player);
        }, 1L);

        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1f, 1f);
    }

    @EventHandler
    public void onPearlLand(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof EnderPearl) {
            EnderPearl pearl = (EnderPearl) event.getEntity();
            if (pearl.getShooter() instanceof Player) {
                Player player = (Player) pearl.getShooter();
                Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("LunarHub"), () -> {
                    pearl.setPassenger(player);
                }, 1L);
            }
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
        }
    }
}