package gg.lunar.hub.util.menu.menu;

import gg.lunar.hub.util.menu.Button;
import gg.lunar.hub.util.menu.Menu;
import gg.lunar.hub.util.menu.buttons.ConfirmationButton;
import gg.lunar.hub.util.menu.fill.FillTemplate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Emilxyz (langgezockt@gmail.com)
 * 30.12.2019 / 04:30
 * iLib / gg.lunar.hub.util.menu.menu
 */


public class ConfirmationMenu extends Menu {

    private String title;
    private Consumer<Boolean> callable;
    private ItemStack info = null;
    private String acceptName = "Confirm";
    private String denyName = "Cancel";

    public ConfirmationMenu(String title, Consumer<Boolean> callable) {
        this.title = title;
        this.callable = callable;
    }

    public ConfirmationMenu(String title, ItemStack info, Consumer<Boolean> callable) {
        this.title = title;
        this.info = info;
        this.callable = callable;
    }

    public ConfirmationMenu(String title, String acceptName, String denyName, Consumer<Boolean> callable) {
        this.title = title;
        this.callable = callable;
        this.acceptName = acceptName;
        this.denyName = denyName;
    }

    public ConfirmationMenu(String title, ItemStack info, String acceptName, String denyName,
                            Consumer<Boolean> callable) {
        this.title = title;
        this.info = info;
        this.callable = callable;
        this.acceptName = acceptName;
        this.denyName = denyName;
    }

    @Override
    public String getTitle(Player player) {
        return title;
    }

    @Override
    public String getRawTitle(Player player) {
        return "";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(3, new ConfirmationButton(true, acceptName, callable));
        if (info != null && info.getType() != Material.AIR)
            buttons.put(4, Button.createPlaceholder(info));
        buttons.put(5, new ConfirmationButton(false, denyName, callable));
        /*for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons.put(((9 * i) + j), new ConfirmationButton(true, acceptName, callable));
                buttons.put((((8 - 9) * i) + j), new ConfirmationButton(false, denyName, callable));
            }
        }*/
        return buttons;
    }

    @Override
    public FillTemplate getFillTemplate() {
        return FillTemplate.FILL;
    }

    @Override
    public ItemStack getPlaceholderItem(Player player) {
        return Button.createPlaceholder(Material.STAINED_GLASS_PANE, (short) 14).getItem(player);
    }
}
