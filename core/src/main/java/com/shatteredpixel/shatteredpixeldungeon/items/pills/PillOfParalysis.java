package com.shatteredpixel.shatteredpixeldungeon.items.pills;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PillOfParalysis extends Pill {
    {
        image = ItemSpriteSheet.PILL_PARAGAS;
    }

    @Override
    public void useEffect(Char appliedCh) {
        super.useEffect(appliedCh);
        Buff.affect(appliedCh, Paralysis.class, 5f);
    }
}
