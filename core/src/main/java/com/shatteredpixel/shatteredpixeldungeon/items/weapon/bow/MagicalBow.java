package com.shatteredpixel.shatteredpixeldungeon.items.weapon.bow;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class MagicalBow extends SpiritBow {
    {
        image = ItemSpriteSheet.MAGICAL_BOW;
    }

    @Override
    public int max(int lvl) {
        int dmg = 6 + (int)(Dungeon.hero.lvl/2.5f)
                + 2* RingOfSharpshooting.levelDamageBonus(Dungeon.hero)
                + (curseInfusionBonus ? 2 + Dungeon.hero.lvl/15 : 0);
        return Math.max(0, Math.round(dmg*((this.enchantment != null) ? 1.1f : 0.7f)));
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        int dmg = super.proc(attacker, defender, damage);

        return dmg;
    }

    @Override
    public String additionalInfo() {
        if (this.enchantment != null) {
            return Messages.get(this, "enchant", Messages.decimalFormat("#", magicMulti(this.buffedLvl())*100));
        } else {
            return Messages.get(this, "no_enchant");
        }
    }

    @Override
    public SpiritArrow knockArrow(){
        Buff.affect(Dungeon.hero, MagicalArrowTracker.class).setMulti(magicMulti(this.buffedLvl()));
        return new MagicalBow.MagicalArrow();
    }

    public class MagicalArrow extends SpiritArrow {
        {
            image = ItemSpriteSheet.ARROW_MAGICAL;
        }
    }

    public static class MagicalArrowTracker extends FlavourBuff {
        public float multi = 1f;
        public void setMulti(float multi) {
            this.multi = multi;
        }
        public float getMulti() {
            return multi;
        }
    };

    public static float magicMulti(int lvl) {
        return 1.5f + 0.25f * lvl;
    }
}
