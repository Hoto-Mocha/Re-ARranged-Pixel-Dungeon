package com.shatteredpixel.shatteredpixeldungeon.items.pills;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PillOfInvisibility extends Pill {
    {
        image = ItemSpriteSheet.PILL_INVIS;
    }

    @Override
    public void useEffect(Char appliedCh) {
        super.useEffect(appliedCh);
        if (appliedCh == Dungeon.hero) {
            Buff.affect(appliedCh, Invisibility.class, 5f);
        } else {
            Buff.affect(appliedCh, Vulnerable.class, 5f);
        }
    }
}
