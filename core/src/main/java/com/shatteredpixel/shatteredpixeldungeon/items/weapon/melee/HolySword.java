/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.HealingArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class HolySword extends MeleeWeapon {

    {
        image = ItemSpriteSheet.HOLY_SWORD;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1f;

        tier = 7;
        alchemy = true;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (hero.STR() >= STRReq()) {
            int healAmt = Math.round(damage * 0.4f);
            healAmt = Math.min( healAmt, attacker.HT - hero.HP );
            attacker.heal(healAmt);
            //Buff.affect(attacker, HealingArea.class).setup(attacker.pos, 3+buffedLvl(), 1, true);
        }
        return super.proc( attacker, defender, damage );
    }

    @Override
    public int max(int lvl) {
        if (Dungeon.hero.STR() >= this.STRReq()) {
            return  8*(tier+1) +
                    lvl*(tier+1);
        } else {
            return  3*(tier-3) +
                    lvl*(tier-3);
        }
    }

    @Override
    public String desc() {
        if (Dungeon.hero.STR() >= this.STRReq()) {
            return Messages.get(this, "true_desc");
        } else {
            return Messages.get(this, "normal_desc");
        }
    }

    @Override
    protected float baseDelay(Char owner) {
        float delay = augment.delayFactor(this.DLY);
        if (owner instanceof Hero) {
            int encumbrance = STRReq() - ((Hero)owner).STR();
            if (encumbrance > 0){
                delay *= Math.pow( 2f, encumbrance );
            } else {
                delay *= 0.5f;
            }
        }
        return delay;
    }

    @Override
    public String name() {
        if (Dungeon.hero.STR() >= this.STRReq()) {
            String trueName = Messages.get(this, "true_name");
            return enchantment != null && (cursedKnown || !enchantment.curse()) ? enchantment.name( trueName ) : trueName;
        } else {
            String name = Messages.get(this, "name");
            return enchantment != null && (cursedKnown || !enchantment.curse()) ? enchantment.name( name ) : name;
        }
    }

    @Override
    public int STRReq(int lvl) {
        return 24;
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {
        {
            inputs =  new Class[]{HugeSword.class, Bible.class, LiquidMetal.class};
            inQuantity = new int[]{1, 1, 100};

            cost = 10;

            output = HolySword.class;
            outQuantity = 1;
        }
    }
}
