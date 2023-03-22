/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2022 Evan Debenham
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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Berserk;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SerialAttack;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfArcana;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class UnformedBlade extends MeleeWeapon {

	public static final String AC_USE		= "USE";
	private static final String TXT_STATUS = "%d%%";
	int chargePerHit;
	int chargeUsePerHit;
	int charge = 0;
	int chargeCap = 100;
	boolean use = false;

	{
		defaultAction = AC_USE;
		image = ItemSpriteSheet.UNFORMED_BLADE;
		hitSound = Assets.Sounds.HIT_STAB;
		hitSoundPitch = 0.9f;
		chargePerHit = 2;
		chargeUsePerHit = 10;

		tier = 5;
	}

	@Override
	public int max(int lvl) {
		return  4*(tier+1) +    //20 base, down from 25
				lvl*(tier+1);   //scaling unchanged
	}

	protected float procChanceMultiplier( Char attacker ){
		float multi = RingOfArcana.enchantPowerMultiplier(attacker);
		Berserk rage = attacker.buff(Berserk.class);
		if (rage != null) {
			multi = rage.enchantFactor(multi);
		}
		if (attacker instanceof Hero && ((Hero) attacker).hasTalent(Talent.ARCANE_ATTACK)) {
			SerialAttack serialAttack = attacker.buff(SerialAttack.class);
			if (serialAttack != null) {
				multi += (serialAttack.getCount() * 0.05f) * ((Hero) attacker).pointsInTalent(Talent.ARCANE_ATTACK);
			}
		}
		if (attacker.buff(Talent.SpiritBladesTracker.class) != null
				&& ((Hero)attacker).pointsInTalent(Talent.SPIRIT_BLADES) == 4){
			multi += 0.1f;
		}
		if (attacker.buff(Talent.StrikingWaveTracker.class) != null
				&& ((Hero)attacker).pointsInTalent(Talent.STRIKING_WAVE) == 4){
			multi += 0.2f;
		}
		if (attacker instanceof Hero && ((Hero) attacker).hasTalent(Talent.MYSTICAL_POWER)) {
			multi += 0.1f * Dungeon.hero.pointsInTalent(Talent.MYSTICAL_POWER);
		}
		return multi;
	}

	@Override
	public int proc( Char attacker, Char defender, int damage ) {

		if (attacker instanceof Hero && !use && defender instanceof Mob && ((Mob) defender).surprisedBy(hero)) {
			charge += chargePerHit;
			if (charge >= chargeCap) {
				charge = chargeCap;
			}
		}

		if (attacker instanceof Hero && Dungeon.hero.subClass == HeroSubClass.WEAPONMASTER) {
			Hero hero = (Hero)attacker;
			Char enemy = hero.enemy();
			if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
				Buff.affect( defender, Bleeding.class ).set( Math.round( 1f+(damage*0.2f)) );
			}
		}

		Item.updateQuickslot();

		if (attacker instanceof Hero && use && defender instanceof Mob && ((Mob) defender).surprisedBy(hero)) {
			charge -= chargeUsePerHit/2;
			int level = Math.max( 0, this.buffedLvl() );

			int enemyHealth = defender.HP - damage;
			if (enemyHealth <= 0) {
				if (charge < chargeUsePerHit) {
					if (charge < 0) {
						charge = 0;
					}
					use = false;
					GLog.w(Messages.get(this, "power_off"));
				}
				return super.proc( attacker, defender, damage ); //no point in proccing if they're already dead.
			}

			//scales from 0 - 50% based on how low hp the enemy is, plus 5% per level
			float maxChance = 0.5f + .05f*level;
			float chanceMulti = (float)Math.pow( ((defender.HT - enemyHealth) / (float)defender.HT), 2);
			float chance;
			chance = maxChance * chanceMulti;

			chance *= procChanceMultiplier(attacker);

			if (Random.Float() < chance) {

				defender.damage( defender.HP, this );
				defender.sprite.emitter().burst( ShadowParticle.UP, 5 );

				charge -= chargeUsePerHit/2; //uses more charge
				if (charge < chargeUsePerHit) {
					if (charge < 0) {
						charge = 0;
					}
					use = false;
					GLog.w(Messages.get(this, "power_off"));
				}
			}
		}
		return super.proc( attacker, defender, damage );
	}

	@Override
	public int damageRoll(Char owner) {
		if (owner instanceof Hero) {
			Hero hero = (Hero)owner;
			Char enemy = hero.enemy();
			if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
				//deals 50% toward max to max on surprise, instead of min to max.
				int diff = max() - min();
				int damage = augment.damageFactor(Random.NormalIntRange(
						min() + Math.round(diff*0.50f),
						max()));
				int exStr = hero.STR() - STRReq();
				if (exStr > 0) {
					damage += Random.IntRange(0, exStr);
				}
				return damage;
			}
		}
		return super.damageRoll(owner);
	}

	@Override
	public String desc() {
		return super.desc() + "\n\n" + Messages.get(this, "energy", chargePerHit, chargeUsePerHit/2, charge, chargeCap);
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped( hero )) {
			actions.add(AC_USE);
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);

		if (action.equals(AC_USE)) {
			if (!isEquipped(hero)) {
				GLog.w(Messages.get(this, "not_equipped"));
			} else {
				if (charge >= chargeUsePerHit) {
					if (use) {
						use = false;
						GLog.n(Messages.get(this, "power_off"));
						curUser.busy();
						Sample.INSTANCE.play(Assets.Sounds.UNLOCK, 2, 1.1f);
						curUser.sprite.operate(curUser.pos);
					} else {
						use = true;
						GLog.p(Messages.get(this, "power_on"));
						curUser.busy();
						Sample.INSTANCE.play(Assets.Sounds.BURNING, 2, 1.1f);
						hero.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10 );
						curUser.sprite.operate(curUser.pos);
					}
				} else {
					GLog.w(Messages.get(this, "no_charge"));
				}
			}
			Item.updateQuickslot();
		}
	}

	private static final String CHARGE = "charge";
	private static final String CHARGE_CAP = "chargeCap";
	private static final String USE = "use";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
		bundle.put(CHARGE_CAP, chargeCap);
		bundle.put(USE, use);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
		chargeCap = bundle.getInt(CHARGE_CAP);
		use = bundle.getBoolean(USE);
	}

	@Override
	public String status() {
		if (super.status() != null) {
			return super.status();
		} else {
			return Messages.format(TXT_STATUS, charge);
		}
	}

	@Override
	public float abilityChargeUse( Hero hero ) {
		return 2*super.abilityChargeUse(hero);
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		Dagger.sneakAbility(hero, 3, this);
	}

}