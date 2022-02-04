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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class HolySword extends MeleeWeapon {

    {
        image = ItemSpriteSheet.HOLY_SWORD;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1f;

        tier = 7;
        //also heals player, see Hero.onAttackComplete
        alchemy = true;
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
            return Messages.get(this, "true_name");
        } else {
            return trueName();
        }
    }

    @Override
    public int STRReq(int lvl) {
        return 21;
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {
        {
            inputs =  new Class[]{Greatsword.class, Bible.class, LiquidMetal.class};
            inQuantity = new int[]{1, 1, 70};

            cost = 10;

            output = HolySword.class;
            outQuantity = 1;
        }
    }
}
