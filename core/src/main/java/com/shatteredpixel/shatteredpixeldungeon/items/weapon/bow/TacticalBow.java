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
    public int min(int lvl) {
        int dmg = 1 + Dungeon.hero.lvl/5
                + (lvl-(int)(Dungeon.hero.lvl/5f))
                + RingOfSharpshooting.levelDamageBonus(Dungeon.hero)
                + (curseInfusionBonus ? 1 + Dungeon.hero.lvl/30 : 0);
        return Math.max(0, dmg);
    }

    @Override
    public int max(int lvl) {
        int dmg = 6 + (int)(Dungeon.hero.lvl/2.5f)
                + 2*(lvl-(int)(Dungeon.hero.lvl/5f))
                + 2*RingOfSharpshooting.levelDamageBonus(Dungeon.hero)
                + (curseInfusionBonus ? 2 + Dungeon.hero.lvl/15 : 0);
        return Math.max(0, dmg);
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
