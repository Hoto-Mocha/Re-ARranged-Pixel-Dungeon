package com.shatteredpixel.shatteredpixeldungeon.items.pills;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class PillOfHealing extends Pill {
    {
        image = ItemSpriteSheet.PILL_HEALING;
    }

    @Override
    public void useEffect(Char appliedCh) {
        super.useEffect(appliedCh);
        if (appliedCh == Dungeon.hero && Dungeon.isChallenged(Challenges.NO_HEALING)){
            PotionOfHealing.pharmacophobiaProc(Dungeon.hero);
        } else {
            Buff.affect(appliedCh, Healing.class).setHeal((int)(appliedCh.HT*0.2f), 0, 1);
        }
    }
}