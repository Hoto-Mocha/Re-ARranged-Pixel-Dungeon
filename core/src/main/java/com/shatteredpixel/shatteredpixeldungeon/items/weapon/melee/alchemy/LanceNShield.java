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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.AntiMagic;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Evolution;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.UpgradeDust;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Glaive;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RoundShield;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class LanceNShield extends MeleeWeapon implements AlchemyWeapon {

    public boolean stance;
    public static final String AC_CHANGE		= "CHANGE";

    {
        image = ItemSpriteSheet.LANCE_N_SHIELD;
        hitSound = Assets.Sounds.HIT_STAB;
        hitSoundPitch = 1.1f;

        tier = 7;
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
                    if (hero.buff(Lance.LanceBuff.class) != null) {
                        hero.buff(Lance.LanceBuff.class).detach();
                    }
                    GLog.p(Messages.get(this,"change_defense"));
                    stance = false;
                } else {
                    GLog.p(Messages.get(this,"change_attack"));
                    stance = true;
                }
                Sample.INSTANCE.play(Assets.Sounds.MISS, 1f, 0.8f);
                hero.sprite.emitter().burst(Speck.factory(Speck.JET), 5);
            }
        }
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        Lance.LanceBuff buff = attacker.buff(Lance.LanceBuff.class);
        if (buff != null && stance) {
            damage *= buff.getDamageFactor();
            buff.detach();
        }
        return super.proc( attacker, defender, damage );
    }

    @Override
    public int reachFactor(Char owner) {
        int reach = super.reachFactor(owner);
        if (stance) {
            reach += 1;
        }
        return reach;
    }

    public static final HashSet<Class> RESISTS = new HashSet<>();

    static {
        RESISTS.addAll(AntiMagic.RESISTS);
    }

    @Override
    public int max(int lvl) {
        if (stance) {
            return  4*(tier-1) +    //24 base
                    lvl*(tier-1);     //scaling +6 per +1
        } else {
            return 4*(tier-2) + //20 base
                    lvl*(tier-2); //+5 per +1
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

    //see Hero.damage for antimagic effects
    public static int drRoll(int level) {
        return Random.NormalIntRange(0, 4+2*level); //4 extra defence, plus 2 per level;
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

    @Override
    public String targetingPrompt() {
        if (stance) {
            return Messages.get(this, "prompt");
        } else {
            return null;
        }
    }

    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        if (stance) {
            Lance.dashAbility(hero, target, this);
        } else {
            RoundShield.guardAbility(hero, 3+buffedLvl(), this);
        }
    }

    @Override
    public String abilityName() {
        if (stance) {
            return Messages.upperCase(Messages.get(this, "ability1_name"));
        } else {
            return Messages.upperCase(Messages.get(this, "ability2_name"));
        }
    }

    @Override
    public String abilityInfo() {
        int amt = levelKnown ? 3+buffedLvl() : 3;
        if (stance) {
            if (levelKnown){
                return Messages.get(this, "ability1_desc", amt);
            } else {
                return Messages.get(this, "typical_ability1_desc", amt);
            }
        } else {
            if (levelKnown){
                return Messages.get(this, "ability2_desc", amt);
            } else {
                return Messages.get(this, "typical_ability2_desc", amt);
            }
        }
    }

    @Override
    public ArrayList<Class<?extends Item>> weaponRecipe() {
        return new ArrayList<>(Arrays.asList(Lance.class, ObsidianShield.class, Evolution.class));
    }

    @Override
    public String discoverHint() {
        return AlchemyWeapon.hintString(weaponRecipe());
    }

    @Override
    public String desc() {
        String info = super.desc();

        info += "\n\n" + AlchemyWeapon.hintString(weaponRecipe());

        return info;
    }

}
