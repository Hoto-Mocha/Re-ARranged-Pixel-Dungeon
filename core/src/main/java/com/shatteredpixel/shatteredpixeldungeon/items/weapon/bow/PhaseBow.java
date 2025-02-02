package com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Projecting;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PhaseBow extends SpiritBow {
    {
        image = ItemSpriteSheet.PHASE_BOW;
    }

    @Override
    public int max(int lvl) {
        int dmg = 6 + (int)(Dungeon.hero.lvl/2.5f)
                + 2* RingOfSharpshooting.levelDamageBonus(Dungeon.hero)
                + (curseInfusionBonus ? 2 + Dungeon.hero.lvl/15 : 0);
        return Math.max(0, Math.round(dmg*0.6f));
    }

    @Override
    public float accuracyFactor(Char owner, Char target) {
        float acc = super.accuracyFactor(owner, target);

        return acc;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        int dmg = super.proc(attacker, defender, damage);

        return dmg;
    }

    @Override
    public SpiritArrow knockArrow(){
        return new PhaseArrow();
    }

    public class PhaseArrow extends SpiritArrow {
        {
            image = ItemSpriteSheet.ARROW_PHASE;
        }

        @Override
        public int throwPos(Hero user, int dst) {
            float distance = 4;
            if (this.hasEnchant(Projecting.class, user)) {
                distance += 4 * Enchantment.genericProcChanceMultiplier(user);
            }

            if ((Dungeon.level.passable[dst] || Dungeon.level.avoid[dst] || Actor.findChar(dst) != null)
                    && Dungeon.level.distance(user.pos, dst) <= Math.round(distance)){
                return dst;
            } else {
                return super.throwPos(user, dst);
            }
        }
    }
}
