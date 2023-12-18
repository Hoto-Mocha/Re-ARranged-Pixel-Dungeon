package com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class TacticalBow extends SpiritBow {

    {
        image = ItemSpriteSheet.TACTICAL_BOW;
    }

    @Override
    public boolean isUpgradable() {
        return true;
    }

    @Override
    public float accuracyFactor(Char owner, Char target) {
        float acc = super.accuracyFactor(owner, target);

        acc *= 1.2f;

        return acc;
    }

    @Override
    public int STRReq(int lvl) {
        return super.STRReq(lvl)+2;
    }

    @Override
    public int level() {
        int level = Dungeon.hero == null ? 0 : Dungeon.hero.lvl/5;
        if (curseInfusionBonus) level += 1 + level/6;
        return level + trueLevel();
    }

    @Override
    public SpiritArrow knockArrow(){
        return new TacticalArrow();
    }

    public class TacticalArrow extends SpiritArrow {
        {
            image = ItemSpriteSheet.ARROW_TACTICAL;
        }
    }

}
