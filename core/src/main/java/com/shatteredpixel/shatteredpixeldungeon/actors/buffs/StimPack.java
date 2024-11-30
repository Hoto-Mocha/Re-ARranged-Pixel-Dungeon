package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;

public class StimPack extends FlavourBuff {
    {
        type = buffType.POSITIVE;
        announced = true;
    }

    @Override
    public int icon() {
        return BuffIndicator.STIMPACK;
    }

    @Override
    public void tintIcon(Image icon) {
        icon.hardlight(0x35A8E9);
    }
}
