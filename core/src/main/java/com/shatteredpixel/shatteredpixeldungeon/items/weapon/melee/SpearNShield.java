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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LanceGuardBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpearGuard;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpearGuardBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.ArcaneResin;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class SpearNShield extends MeleeWeapon {

    {
        image = ItemSpriteSheet.SPEAR_N_SHIELD;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1.1f;

        tier = 3;
    }

    @Override
    public int max(int lvl) {
        return  3*(tier+1) +
                lvl*(tier+1);
    }

    @Override
    public int proc(Char attacker, Char defender, int damage ) {

        // lvl 0 - 33%
        // lvl 1 - 50%
        // lvl 2 - 60%

        float procChance = (buffedLvl()+1f)/(buffedLvl()+3f);

        if (Random.Float() < procChance && (Dungeon.hero.buff(SpearGuardBuff.class) == null) && (Dungeon.hero.buff(SpearGuard.class) == null)) {
            Buff.prolong( attacker, SpearGuardBuff.class, 5f);
            return super.proc( attacker, defender, damage );
        } else {
            return super.proc( attacker, defender, damage );
        }
    }

    public String statsInfo(){
        if (isIdentified()){
            return Messages.get(this, "stats_desc", buffedLvl(), 4+2*buffedLvl());
        } else {
            return Messages.get(this, "typical_stats_desc", 0, 3);
        }
    }

    @Override
    protected float baseDelay( Char owner ){
        float delay = augment.delayFactor(this.DLY);
        if (Dungeon.hero.buff(SpearGuard.class) != null) {
            delay *= 0.5f;
        }

        return delay;
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{Spear.class, RoundShield.class, LiquidMetal.class};
            inQuantity = new int[]{1, 1, 30};

            cost = 10;

            output = SpearNShield.class;
            outQuantity = 1;
        }

    }
}
