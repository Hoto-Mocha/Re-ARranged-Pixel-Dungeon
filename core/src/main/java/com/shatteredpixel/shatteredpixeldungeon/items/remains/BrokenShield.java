package com.shatteredpixel.shatteredpixeldungeon.items.remains;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

public class BrokenShield extends RemainsItem {
    {
        image = ItemSpriteSheet.BROKEN_SHIELD;
    }

    @Override
    protected void doEffect(Hero hero) {
        Buff.affect(hero, DRBuff.class);
    }

    public static class DRBuff extends Buff {
        {
            type = buffType.POSITIVE;
            announced = true;
        }

        @Override
        public int icon() {
            return BuffIndicator.HOLY_MANTLE;
        }
    }
}
