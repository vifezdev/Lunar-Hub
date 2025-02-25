package gg.lunar.hub.user.listener;

import gg.lunar.hub.LunarHub;
import gg.lunar.hub.user.User;
import gg.lunar.hub.util.CC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;

public class ChatListener implements Listener {

    private final LunarHub plugin;

    public ChatListener(LunarHub plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();
        User senderUser = plugin.getUserManager().getUser(sender.getUniqueId());

        if (!senderUser.isGlobalChatEnabled()) {
            sender.sendMessage(CC.translate("&cYou have public messages disabled! Enable it in settings."));
            event.setCancelled(true);
            return;
        }

        event.getRecipients().removeIf(player -> {
            User user = plugin.getUserManager().getUser(player.getUniqueId());
            return user != null && !user.isGlobalChatEnabled();
        });
    }
}
