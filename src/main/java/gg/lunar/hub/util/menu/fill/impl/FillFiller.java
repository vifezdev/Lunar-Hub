package gg.lunar.hub.util.menu.fill.impl;

import gg.lunar.hub.util.menu.Button;
import gg.lunar.hub.util.menu.Menu;
import gg.lunar.hub.util.menu.fill.IMenuFiller;
import gg.lunar.hub.util.menu.page.PagedMenu;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * @author Emilxyz (langgezockt@gmail.com)
 * 17.12.2020 / 21:21
 * iLib / gg.lunar.hub.util.menu.fill.implement
 */

public class FillFiller implements IMenuFiller {

    @Override
    public void fill(Menu menu, Player player, Map<Integer, Button> buttons, int size) {
        for (int i = menu instanceof PagedMenu ? 8 : 0; i < size; i++)
            buttons.putIfAbsent(i, Button.createPlaceholder(menu.getPlaceholderItem(player)));
    }

}
