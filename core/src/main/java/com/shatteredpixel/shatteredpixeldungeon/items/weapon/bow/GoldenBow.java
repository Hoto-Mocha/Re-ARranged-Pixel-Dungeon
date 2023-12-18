package com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.plants.Blindweed;
import com.shatteredpixel.shatteredpixeldungeon.plants.Firebloom;
import com.shatteredpixel.shatteredpixeldungeon.plants.Icecap;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sorrowmoss;
import com.shatteredpixel.shatteredpixeldungeon.plants.Stormvine;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

public class GoldenBow extends SpiritBow {

    {
        image = ItemSpriteSheet.GOLDEN_BOW;
    }

    @Override
    public int max(int lvl) {
        int dmg = 6 + (int)(Dungeon.hero.lvl/2.5f)
                + 2*RingOfSharpshooting.levelDamageBonus(Dungeon.hero)
                + (curseInfusionBonus ? 2 + Dungeon.hero.lvl/15 : 0);
        return Math.max(0, Math.round(dmg*1.2f));
    }

    @Override
    public float accuracyFactor(Char owner, Char target) {
        float acc = super.accuracyFactor(owner, target);

        acc *= 1.2f;

        return acc;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        int dmg = super.proc(attacker, defender, damage);

        return dmg;
    }

    @Override
    public SpiritArrow knockArrow(){
        return new GoldenArrow();
    }

    public class GoldenArrow extends SpiritArrow {
        {
            image = ItemSpriteSheet.ARROW_GOLD;
        }
    }

}
