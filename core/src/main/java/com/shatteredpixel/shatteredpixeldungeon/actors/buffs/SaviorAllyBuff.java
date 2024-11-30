package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;

public class SaviorAllyBuff extends AllyBuff {
    {
        type = buffType.NEUTRAL;
    }

    @Override
    public void fx(boolean on) {
        if (on) target.sprite.add(CharSprite.State.HEARTS);
        else    target.sprite.remove(CharSprite.State.HEARTS);
    }
}
