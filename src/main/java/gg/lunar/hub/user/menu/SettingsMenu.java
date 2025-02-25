package gg.lunar.hub.user.menu;

import gg.lunar.hub.LunarHub;
import gg.lunar.hub.user.User;
import gg.lunar.hub.util.CC;
import gg.lunar.hub.util.ItemBuilder;
import gg.lunar.hub.util.menu.Button;
import gg.lunar.hub.util.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class SettingsMenu extends Menu {

    private final LunarHub plugin;

    @Override
    public String getTitle(Player player) {
        return CC.translate("&bSettings");
    }

    @Override
    public String getRawTitle(Player player) {
        return CC.translate("&bSettings");
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (int i = 0; i < 27; i++) {
            buttons.put(i, Button.createPlaceholder(" ", Material.STAINED_GLASS_PANE, (short) 7));
        }

        buttons.put(11, new ScoreboardToggleButton(plugin));
        buttons.put(15, new GlobalChatToggleButton(plugin));

        return buttons;
    }

    @Override
    public boolean isAutoUpdate() {
        return true;
    }

    private class ScoreboardToggleButton extends Button {
        private final LunarHub plugin;

        public ScoreboardToggleButton(LunarHub plugin) {
            this.plugin = plugin;
        }

        @Override
        public ItemStack getItem(Player player) {
            boolean enabled = isScoreboardEnabled(player);
            return new ItemBuilder(Material.PAINTING)
                    .setDisplayName(CC.translate("&b&lScoreboard Visibility"))
                    .setLore(Arrays.asList(
                            CC.translate("&7Toggle the scoreboard."),
                            " ",
                            CC.translate(enabled ? "&a■ &aYes" : "&7■ &cYes"),
                            CC.translate(enabled ? "&7■ &cNo" : "&a■ &aNo"),
                            " ",
                            CC.translate("&bClick to change!")
                    ))
                    .build();
        }

        @Override
        public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
            boolean enabled = isScoreboardEnabled(player);
            setScoreboardEnabled(player, !enabled);

            playClickSound(player);
            new SettingsMenu(plugin).openMenu(player);
        }

        private boolean isScoreboardEnabled(Player player) {
            return plugin.getUserManager().getUser(player.getUniqueId()).isScoreboardEnabled();
        }

        private void setScoreboardEnabled(Player player, boolean enabled) {
            User user = plugin.getUserManager().getUser(player.getUniqueId());
            if (user != null) {
                user.setScoreboardEnabled(enabled);
                plugin.getUserManager().saveUser(player.getUniqueId());
            }
        }
    }

    private class GlobalChatToggleButton extends Button {
        private final LunarHub plugin;

        public GlobalChatToggleButton(LunarHub plugin) {
            this.plugin = plugin;
        }

        @Override
        public ItemStack getItem(Player player) {
            boolean chatEnabled = isGlobalChatEnabled(player);
            return new ItemBuilder(Material.PAPER)
                    .setDisplayName(CC.translate("&b&lGlobal Chat"))
                    .setLore(Arrays.asList(
                            CC.translate("&7See public messages."),
                            " ",
                            CC.translate(chatEnabled ? "&a■ &aYes" : "&7■ &cYes"),
                            CC.translate(chatEnabled ? "&7■ &cNo" : "&a■ &aNo"),
                            " ",
                            CC.translate("&bClick to change!")
                    ))
                    .build();
        }

        @Override
        public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
            boolean chatEnabled = isGlobalChatEnabled(player);
            setGlobalChatEnabled(player, !chatEnabled);

            playClickSound(player);
            new SettingsMenu(plugin).openMenu(player);
        }

        private boolean isGlobalChatEnabled(Player player) {
            return plugin.getUserManager().getUser(player.getUniqueId()).isGlobalChatEnabled();
        }

        private void setGlobalChatEnabled(Player player, boolean enabled) {
            User user = plugin.getUserManager().getUser(player.getUniqueId());
            if (user != null) {
                user.setGlobalChatEnabled(enabled);
                plugin.getUserManager().saveUser(player.getUniqueId());

                player.sendMessage(CC.translate(enabled ? "&aPublic messages enabled." : "&cPublic messages hidden."));
            }
        }
    }

    private void playClickSound(Player player) {
        String soundName = plugin.getConfig().getString("SETTINGS.SOUND", "LEVEL_UP");

        try {
            Sound sound = Sound.valueOf(soundName.toUpperCase());
            player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
        } catch (IllegalArgumentException e) {
        }
    }
}
