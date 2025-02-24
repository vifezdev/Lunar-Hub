package gg.lunar.hub.util.menu.buttons;

import gg.lunar.hub.util.CC;
import gg.lunar.hub.util.ItemBuilder;
import gg.lunar.hub.util.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * @author Emilxyz (langgezockt@gmail.com)
 * 30.12.2019 / 04:32
 * iLib / gg.lunar.hub.util.menu.buttons
 */

public class ConfirmationButton extends Button {

    private boolean bool;
    private Consumer<Boolean> callable;
    private String name;

    public ConfirmationButton(boolean bool, Consumer<Boolean> callable) {
        this.bool = bool;
        this.callable = callable;
    }

    public ConfirmationButton(boolean bool, String name, Consumer<Boolean> callable) {
        this.bool = bool;
        this.callable = callable;
        this.name = name;
    }

    @Override
    public ItemStack getItem(Player player) {
        return new ItemBuilder(Material.WOOL, (short) (bool ? 5 : 14))
                .setDisplayName(bool ? CC.GREEN + CC.BOLD + name : CC.RED + CC.BOLD + name)
                .build();
    }

    @Override
    public void click(Player player, int slot, ClickType clickType, int hotbarButton) {
        player.closeInventory();
        callable.accept(bool);
    }
}
