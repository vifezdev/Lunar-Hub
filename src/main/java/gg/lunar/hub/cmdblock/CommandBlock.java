package gg.lunar.hub.cmdblock;

import gg.lunar.hub.LunarHub;
import gg.lunar.hub.util.CC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandBlock implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        boolean enabled = LunarHub.getInstance().getConfig().getBoolean("BLOCKED-COMMANDS.ENABLED");
        List<String> blockedCommands = LunarHub.getInstance().getConfig().getStringList("BLOCKED-COMMANDS.LIST");
        String noPermissionMessage = CC.translate(LunarHub.getInstance().getConfig().getString("BLOCKED-COMMANDS.NO-PERMISSION", "&cYou don't have permission to do this command!"));

        if (!enabled) {
            return;
        }

        String command = event.getMessage().toLowerCase().split(" ")[0].substring(1);

        if (event.getPlayer().hasPermission("hub.command.block.bypass")) {
            return;
        }

        if (blockedCommands.contains(command)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(noPermissionMessage);
        }
    }
}