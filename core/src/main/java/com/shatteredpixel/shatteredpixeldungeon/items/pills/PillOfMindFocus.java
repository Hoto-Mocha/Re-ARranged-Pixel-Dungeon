package com.shatteredpixel.shatteredpixeldungeon.items.pills;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MindVision;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PillOfMindFocus extends Pill {
    {
        image = ItemSpriteSheet.PILL_MINDVIS;
    }

    @Override
    public void useEffect(Char appliedCh) {
        super.useEffect(appliedCh);
        if (appliedCh == Dungeon.hero) {
            Buff.affect(appliedCh, MindVision.class, 3f);
        } else {
            Buff.affect(appliedCh, Blindness.class, 8f);
        }
    }
}
