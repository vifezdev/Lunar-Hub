package gg.lunar.hub.util.menu.listener;

import gg.lunar.hub.util.menu.Button;
import gg.lunar.hub.util.menu.Menu;
import gg.lunar.hub.util.menu.page.PagedMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;

/**
 * @author langgezockt (langgezockt@gmail.com)
 * 22.06.2019 / 11:51
 * iUtils / cc.invictusgames.iutils.utils.menu
 */

public class MenuListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Menu menu = Menu.getOpenedMenus().get(player);

        if (menu == null) {
            return;
        }

        if (event.getClickedInventory() == null) {
            return;
        }

        if (event.getClickedInventory() != player.getOpenInventory().getTopInventory()) {
            if (menu.cancelLowerClicks())
                event.setCancelled(true);
            return;
        }

        if (menu.cancelClicks())
            event.setCancelled(true);

        int slot = event.getSlot();
        Map<Integer, Button> buttons = menu.getButtons(player);

        if (buttons.containsKey(slot)) {
            Button button = buttons.get(slot);
            button.click(player, slot, event.getClick(), event.getHotbarButton());
            event.setCancelled(button.isCancelClick());
            Button.ButtonClickSound sound = button.getClickSound(player);
            if (sound != null) {
                player.playSound(player.getLocation(), sound.getSound(), sound.getVolume(), sound.getPitch());
            }
            if (menu.isClickUpdate()) {
                menu.updateInventory(player, menu instanceof PagedMenu);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Menu menu = Menu.getOpenedMenus().get(player);

        if (menu == null) {
            return;
        }

        menu.setCancelIncomingUpdates(true);
        menu.onClose(player);
        if (menu.getUpdateRunnable() != null) {
            menu.getUpdateRunnable().cancel();
            menu.setUpdateRunnable(null);
        }
        Menu.getOpenedMenus().remove(player);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Menu menu = Menu.getOpenedMenus().get(player);

        if (menu == null) {
            return;
        }

        menu.onClose(player);
        menu.getUpdateRunnable().cancel();
        menu.setUpdateRunnable(null);
        Menu.getOpenedMenus().remove(player);
    }
}
