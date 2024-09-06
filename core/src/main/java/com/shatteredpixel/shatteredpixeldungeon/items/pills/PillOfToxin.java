package com.shatteredpixel.shatteredpixeldungeon.items.pills;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PillOfToxin extends Pill {
    {
        image = ItemSpriteSheet.PILL_TOXICGAS;
    }

    @Override
    public void useEffect(Char appliedCh) {
        super.useEffect(appliedCh);
        Buff.affect(appliedCh, Poison.class).set(5 + Math.round(Dungeon.depth/2f));
    }
}
