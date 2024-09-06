package com.shatteredpixel.shatteredpixeldungeon.items.pills;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AdrenalineSurge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Daze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PillOfAwakening extends Pill {
    {
        image = ItemSpriteSheet.PILL_STRENGTH;
        talentFactor = 1;
        talentChance = 1;
        enemyAlternative = true;
        unique = true;
        bones = false;
    }

    @Override
    public void useEffect(Char appliedCh) {
        super.useEffect(appliedCh);
        if (appliedCh == Dungeon.hero) {
            Buff.affect(appliedCh, AdrenalineSurge.class).reset(2, 500);
        } else {
            Buff.affect(appliedCh, Vulnerable.class, 20f);
            Buff.affect(appliedCh, Vertigo.class, 20f);
            Buff.affect(appliedCh, Weakness.class, 20f);
            Buff.affect(appliedCh, Daze.class, 20f);
        }
    }

    @Override
    public int value() {
        return 25 * quantity;
    }

    @Override
    public int energyVal() {
        return 5 * quantity;
    }
}
