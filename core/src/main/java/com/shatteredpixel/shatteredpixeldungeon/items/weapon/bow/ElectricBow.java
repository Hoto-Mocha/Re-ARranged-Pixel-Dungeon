package com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Electricity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ThunderImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class ElectricBow extends SpiritBow {
    {
        image = ItemSpriteSheet.ELECTRIC_BOW;
    }

    @Override
    public int max(int lvl) {
        int dmg = 6 + (int)(Dungeon.hero.lvl/2.5f)
                + 2* RingOfSharpshooting.levelDamageBonus(Dungeon.hero)
                + (curseInfusionBonus ? 2 + Dungeon.hero.lvl/15 : 0);
        return Math.max(0, Math.round(dmg*0.8f));
    }

    @Override
    public float accuracyFactor(Char owner, Char target) {
        float acc = super.accuracyFactor(owner, target);

        return acc;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        int dmg = super.proc(attacker, defender, damage);
        if (!defender.isImmune(Electricity.class)) {
            defender.damage(Hero.heroDamageIntRange(min(this.level()), max(this.level())), new Electricity());
        }
        ThunderImbue.thunderEffect(defender.sprite);
        return dmg;
    }

    @Override
    public SpiritArrow knockArrow(){
        return new ElectricBow.ElectricArrow();
    }

    public class ElectricArrow extends SpiritArrow {
        {
            image = ItemSpriteSheet.ARROW_ELECTRIC;
        }
    }
}
