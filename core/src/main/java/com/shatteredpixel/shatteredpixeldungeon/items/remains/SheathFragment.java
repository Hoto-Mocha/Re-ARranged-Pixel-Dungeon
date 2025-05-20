package com.shatteredpixel.shatteredpixeldungeon.items.remains;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Sheath;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class SheathFragment extends RemainsItem {
    {
        image = ItemSpriteSheet.SHEATH_FRAGMENT;
    }

    @Override
    protected void doEffect(Hero hero) {
        Buff.affect(hero, Sheath.CertainCrit.class).set(1);
    }
}
