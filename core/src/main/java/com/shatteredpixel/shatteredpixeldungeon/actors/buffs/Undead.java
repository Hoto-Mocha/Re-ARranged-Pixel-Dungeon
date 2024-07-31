package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

public class Undead extends FlavourBuff {

    public static final float DURATION	= 3f;

    {
        type = buffType.POSITIVE;
        announced = true;
    }

    @Override
    public void detach() {
        super.detach();
        target.die(this);
    }

    @Override
    public void fx(boolean on) {
        if (on) target.sprite.add( CharSprite.State.DARKENED );
        else if (target.invisible == 0) target.sprite.remove( CharSprite.State.DARKENED );
    }

    @Override
    public int icon() {
        return BuffIndicator.CORRUPT;
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, (DURATION - visualcooldown()) / DURATION);
    }

}
