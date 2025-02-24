package gg.lunar.hub.util.menu.fill;

import gg.lunar.hub.util.menu.Button;
import gg.lunar.hub.util.menu.Menu;
import org.bukkit.entity.Player;

import java.util.Map;


/**
 * @author Emilxyz (langgezockt@gmail.com)
 * 17.12.2020 / 21:20
 * iLib / gg.lunar.hub.util.menu.fill
 */

public interface IMenuFiller {

    void fill(Menu menu, Player player, Map<Integer, Button> buttons, int size);

}
