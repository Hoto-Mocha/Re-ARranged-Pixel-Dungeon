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
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class SpearNShield extends MeleeWeapon {

    public boolean stance;
    public static final String AC_CHANGE		= "CHANGE";

    {
        image = ItemSpriteSheet.SPEAR_N_SHIELD;
        hitSound = Assets.Sounds.HIT_STAB;
        hitSoundPitch = 1.1f;

        tier = 3;
        alchemy = true;
        stance = true;

        defaultAction = AC_CHANGE;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (isEquipped(hero)) {
            actions.add(AC_CHANGE);
        }
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_CHANGE)) {
            if (!isEquipped(hero)) {
                GLog.w(Messages.get(this,"not_equipped"));
            } else {
                if (stance) {
                    stance = false;
                } else {
                    stance = true;
                }
                Sample.INSTANCE.play(Assets.Sounds.MISS, 1f, 0.8f);
                hero.sprite.emitter().burst(Speck.factory(Speck.JET), 5);
                GLog.p(Messages.get(this,"change"));
            }
        }
    }

    @Override
    public int reachFactor(Char owner) {
        int reach = super.reachFactor(owner);
        if (stance) {
            reach += 1;
        }
        return reach;
    }

    @Override
    public int max(int lvl) {
        if (stance) {
            return  Math.round(6.67f*(tier)) +    //20 base, up from 15
                    lvl*Math.round(1.33f*(tier)); //+4 per level, up from +3
        } else {
            return  Math.round(2.5f*(tier+1)) +     //10 base, down from 20
                    lvl*(tier-1);                   //+2 per level, down from +4
        }
    }

    @Override
    public int defenseFactor( Char owner ) {
        if (stance) {
            return 0;
        } else {
            return 4+2*buffedLvl();     //4 extra defence, plus 2 per level;
        }
    }

    public String statsInfo(){
        if (stance) {
            if (isIdentified()){
                return Messages.get(this, "stats_desc_attack");
            } else {
                return Messages.get(this, "typical_stats_desc_attack");
            }
        } else {
            if (isIdentified()){
                return Messages.get(this, "stats_desc_defense", 4+2*buffedLvl());
            } else {
                return Messages.get(this, "typical_stats_desc_defense", 4);
            }
        }
    }

    @Override
    protected float baseDelay( Char owner ){
        float delay = augment.delayFactor(this.DLY);
        if (stance) {
            delay *= 1.5f;
        } else {
            delay *= 1f;
        }
        return delay;
    }

    private static final String STANCE = "stance";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put( STANCE, stance );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        stance = bundle.getBoolean( STANCE );
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{Spear.class, RoundShield.class, LiquidMetal.class};
            inQuantity = new int[]{1, 1, 30};

            cost = 5;

            output = SpearNShield.class;
            outQuantity = 1;
        }

    }
}
