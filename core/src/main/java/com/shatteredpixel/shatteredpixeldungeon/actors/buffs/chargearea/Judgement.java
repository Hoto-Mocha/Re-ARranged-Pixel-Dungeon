package com.shatteredpixel.shatteredpixeldungeon.actors.buffs.chargearea;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;

public class Judgement extends FlavourBuff {

    public static final float DURATION = 5f;

    {
        type = buffType.NEUTRAL;
        announced = true;
    }

    @Override
    public int icon() {
        return BuffIndicator.JUDGEMENT;
    }

    @Override
    public void tintIcon(Image icon) {
        icon.tint(0xFFFF66);
        super.tintIcon(icon);
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, (DURATION - visualcooldown()) / DURATION);
    }

}
