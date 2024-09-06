package com.shatteredpixel.shatteredpixeldungeon.items.pills;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PillOfAcceleration extends Pill {
    {
        image = ItemSpriteSheet.PILL_HASTE;
    }

    @Override
    public void useEffect(Char appliedCh) {
        super.useEffect(appliedCh);
        Buff.affect(appliedCh, Swiftthistle.TimeBubble.class).reset();
    }
}
