package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;

public class PainKiller extends FlavourBuff {
    {
        type = buffType.POSITIVE;
        announced = true;
    }

    @Override
    public int icon() {
        return BuffIndicator.PARRY;
    }

    @Override
    public void tintIcon(Image icon) {
        icon.hardlight(0x00FFFF);
    }
}
