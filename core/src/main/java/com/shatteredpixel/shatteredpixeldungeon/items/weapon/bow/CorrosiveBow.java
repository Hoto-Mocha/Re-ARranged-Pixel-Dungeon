package com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class CorrosiveBow extends SpiritBow {

    {
        image = ItemSpriteSheet.CORROSION_BOW;
    }

    @Override
    public int max(int lvl) {
        int dmg = 6 + (int)(Dungeon.hero.lvl/2.5f)
                + 2*RingOfSharpshooting.levelDamageBonus(Dungeon.hero)
                + (curseInfusionBonus ? 2 + Dungeon.hero.lvl/15 : 0);
        return Math.max(min(lvl), Math.round(dmg*0.7f));
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        int dmg = super.proc(attacker, defender, damage);
        if (defender != null && defender.isAlive() && defender != curUser) {
            if (defender.buff(Corrosion.class) != null) {
                Buff.affect(defender, Corrosion.class).add(1+ Random.Int(3), 1);
            } else {
                Buff.affect(defender, Corrosion.class).set(1+Random.Int(3), 1);
            }
        }
        return dmg;
    }

    @Override
    public SpiritArrow knockArrow(){
        return new CorrosiveArrow();
    }

    public class CorrosiveArrow extends SpiritArrow {
        {
            image = ItemSpriteSheet.ARROW_CORROSION;
        }
    }

}
