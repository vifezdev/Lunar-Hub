package gg.lunar.hub.util.menu.fill.impl;

import gg.lunar.hub.util.menu.Button;
import gg.lunar.hub.util.menu.Menu;
import gg.lunar.hub.util.menu.fill.IMenuFiller;
import gg.lunar.hub.util.menu.page.PagedMenu;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * @author Emilxyz (langgezockt@gmail.com)
 * 17.12.2020 / 21:22
 * iLib / gg.lunar.hub.util.menu.fill.impl
 */

public class BorderFiller implements IMenuFiller {

    @Override
    public void fill(Menu menu, Player player, Map<Integer, Button> buttons, int size) {
        int startIndex = menu instanceof PagedMenu ? 8 : 0;
        for (int i = startIndex; i < size; i++) {
            if (i < startIndex + 9) {
                buttons.putIfAbsent(i, Button.createPlaceholder(menu.getPlaceholderItem(player)));
                buttons.putIfAbsent(i + (size - 9), Button.createPlaceholder(menu.getPlaceholderItem(player)));
            }

            if (i % 9 == 0) {
                buttons.putIfAbsent(i, Button.createPlaceholder(menu.getPlaceholderItem(player)));
                buttons.putIfAbsent(i + 8, Button.createPlaceholder(menu.getPlaceholderItem(player)));
            }
        }
    }

}
