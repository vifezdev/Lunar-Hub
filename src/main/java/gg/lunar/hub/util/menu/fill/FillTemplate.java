package gg.lunar.hub.util.menu.fill;

import gg.lunar.hub.util.menu.fill.impl.BorderFiller;
import gg.lunar.hub.util.menu.fill.impl.FillFiller;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Emilxyz (langgezockt@gmail.com)
 * 17.12.2020 / 21:19
 * iLib / gg.lunar.hub.util.menu.fill
 */

@RequiredArgsConstructor
@Getter
public enum FillTemplate {

    FILL(new FillFiller()),
    BORDER(new BorderFiller());

    private final IMenuFiller menuFiller;

}
