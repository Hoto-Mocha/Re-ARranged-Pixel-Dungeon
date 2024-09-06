package com.shatteredpixel.shatteredpixeldungeon.items.pills;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AllyBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LostInventory;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PillOfAntibiotics extends Pill {
    {
        image = ItemSpriteSheet.PILL_PURITY;
    }

    @Override
    public void useEffect(Char appliedCh) {
        super.useEffect(appliedCh);
        for (Buff buff : appliedCh.buffs()) {
            if (buff.type == Buff.buffType.NEGATIVE
                    && !(buff instanceof AllyBuff)
                    && !(buff instanceof LostInventory)
                    && !(buff instanceof Hunger)){
                buff.detach();
            }
        }
    }
}
