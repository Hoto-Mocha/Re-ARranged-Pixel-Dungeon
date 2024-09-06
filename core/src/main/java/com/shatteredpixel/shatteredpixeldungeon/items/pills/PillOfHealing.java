package com.shatteredpixel.shatteredpixeldungeon.items.pills;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PillOfHealing extends Pill {
    {
        image = ItemSpriteSheet.PILL_HEALING;
    }

    @Override
    public void useEffect(Char appliedCh) {
        super.useEffect(appliedCh);
        Buff.affect(appliedCh, Healing.class).setHeal((int)(appliedCh.HT*0.2f), 0, 1);
    }
}