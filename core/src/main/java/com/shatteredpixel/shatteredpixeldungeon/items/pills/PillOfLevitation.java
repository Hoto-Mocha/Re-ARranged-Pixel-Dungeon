package com.shatteredpixel.shatteredpixeldungeon.items.pills;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Levitation;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PillOfLevitation extends Pill {
    {
        image = ItemSpriteSheet.PILL_LEVITATE;
        enemyAlternative = true;
    }

    @Override
    public void useEffect(Char appliedCh) {
        super.useEffect(appliedCh);
        if (appliedCh == Dungeon.hero) {
            Buff.affect(appliedCh, Levitation.class, 5f);
        } else {
            Buff.affect(appliedCh, Vertigo.class, 5f);
        }
    }
}
