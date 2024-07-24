package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

public class DeathSword extends MeleeWeapon {

    {
        image = ItemSpriteSheet.DEATHS_SWORD;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1f;
        levelKnown = true;

        tier = 4;
        unique = true;
        bones = false;
    }

    @Override
    public int max(int lvl) {
        return  5*(tier) +                	//20 base, down from 25
                Math.round(lvl*(tier+1));	//+5 per level
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (attacker instanceof Hero
                && damage >= defender.HP
                && defender instanceof Mob
                && ((Hero) attacker).lvl <= ((Mob) defender).maxLvl + 2) {
            Buff.affect(attacker, MaxHPBoost.class).kill();
        }
        return super.proc(attacker, defender, damage);
    }

    @Override
    public int level() {
        int level = Dungeon.hero == null ? 0 : Dungeon.hero.lvl/5;
        if (curseInfusionBonus) level += 1 + level/6;
        return level;
    }

    @Override
    public String desc() {
        int HTBoost = 0;
        if (Dungeon.hero.buff(MaxHPBoost.class) != null) {
            HTBoost = Dungeon.hero.buff(MaxHPBoost.class).HTBonus();
        }
        return Messages.get(this, "desc", HTBoost, 15+Dungeon.hero.lvl*5);
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    public static class MaxHPBoost extends Buff {
        {
            type = buffType.POSITIVE;
        }

        private int stack = 0;

        public void kill() {
            stack = Math.min(stack+1, 15+Dungeon.hero.lvl*5);
            Dungeon.hero.updateHT(false);
        }

        public int HTBonus() {
            return stack;
        }

        private static final String STACK = "stack";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(STACK, stack);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            stack = bundle.getInt(STACK);
        }
    }
}
