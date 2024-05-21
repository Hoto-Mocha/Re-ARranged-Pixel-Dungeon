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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Evolution;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Bible;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;
import java.util.Arrays;

public class HolySword extends MeleeWeapon implements AlchemyWeapon {

    {
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1f;

        tier = 7;
    }

    @Override
    public int image() {
        if (Dungeon.hero.STR() < this.STRReq()){
            return ItemSpriteSheet.HOLYSWORD;
        } else {
            return ItemSpriteSheet.HOLYSWORD_TRUE;
        }
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (Dungeon.hero.STR() < this.STRReq()){
            actions.remove(AC_ABILITY);
        }
        return actions;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (hero.STR() >= STRReq()) {
            int healAmt = Math.round(damage * 0.4f);
            healAmt = Math.min( healAmt, attacker.HT - hero.HP );
            attacker.heal(healAmt);
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

//    public String swordName() {
//        if (Dungeon.hero.STR() >= this.STRReq()) {
//            return Messages.get(this, "true_name");
//        } else {
//            return Messages.get(this, "name");
//        }
//    }

    public String abilityName() {
        if (Dungeon.hero.STR() >= this.STRReq()) {
            return Messages.upperCase(Messages.get(this, "true_ability_name"));
        } else {
            return Messages.upperCase(Messages.get(this, "ability_name"));
        }
    }

    @Override
    public int STRReq(int lvl) {
        return 24;
    }

    @Override
    protected int baseChargeUse(Hero hero, Char target){
        return 1;
    }

    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        beforeAbilityUsed(hero, null);
        Buff.affect(hero, Barrier.class).setShield(Math.round(curUser.HT*0.2f));
        hero.sprite.operate(hero.pos);
        Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
        hero.next();
        afterAbilityUsed(hero);
    }

    @Override
    public String abilityInfo() {
        if (Dungeon.hero.STR() >= this.STRReq()) {
            return Messages.get(this, "true_ability_desc");
        } else {
            return Messages.get(this, "ability_desc");
        }
    }

    @Override
    public ArrayList<Class<?extends Item>> weaponRecipe() {
        return new ArrayList<>(Arrays.asList(Bible.class, HugeSword.class, Evolution.class));
    }

}
