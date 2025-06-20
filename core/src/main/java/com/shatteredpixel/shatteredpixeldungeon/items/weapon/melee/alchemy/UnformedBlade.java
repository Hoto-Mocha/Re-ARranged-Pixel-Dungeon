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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Evolution;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.UpgradeDust;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssassinsBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Dagger;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;

public class UnformedBlade extends MeleeWeapon implements AlchemyWeapon {

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

	@Override
	public int proc( Char attacker, Char defender, int damage ) {

		if (attacker instanceof Hero && defender instanceof Mob && ((Mob) defender).surprisedBy(hero)) {
			if (!use) {
				charge += chargePerHit;
				if (charge >= chargeCap) {
					charge = chargeCap;
				}
			} else {
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

				chance *= Weapon.Enchantment.genericProcChanceMultiplier(attacker);

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
		}

		Item.updateQuickslot();
		return super.proc( attacker, defender, damage );
	}

	@Override
	public int damageRoll(Char owner) {
		if (owner instanceof Hero) {
			Hero hero = (Hero)owner;
			Char enemy = hero.attackTarget();
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
		String info = super.desc();

		info += Messages.get(this, "energy", chargePerHit, chargeUsePerHit/2, charge, chargeCap);
		info += "\n\n" + AlchemyWeapon.hintString(weaponRecipe());

		return info;
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
	public String targetingPrompt() {
		return Messages.get(Dagger.class, "prompt");
	}

	public boolean useTargeting(){
		return false;
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		Dagger.sneakAbility(hero, target, 2, 2+buffedLvl(), this);
	}

	@Override
	public String abilityInfo() {
		if (levelKnown){
			return Messages.get(this, "ability_desc", 2+buffedLvl());
		} else {
			return Messages.get(this, "typical_ability_desc", 2);
		}
	}

	@Override
	public ArrayList<Class<?extends Item>> weaponRecipe() {
		return new ArrayList<>(Arrays.asList(AssassinsBlade.class, UpgradeDust.class, Evolution.class));
	}

	@Override
	public String discoverHint() {
		return AlchemyWeapon.hintString(weaponRecipe());
	}

}