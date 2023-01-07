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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Lightning;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.EnergyParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Shocking;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class EnergyWeapon extends MeleeWeapon {

	public static final String AC_POWER		= "POWER";
	private static final String TXT_STATUS = "%d%%";
	int chargePerHit;
	int chargeUsePerHit;
	int charge = 0;
	int chargeCap = 100;
	boolean power = false;
	{
		defaultAction = AC_POWER;
		alchemy = true;

		//bones = false; //only for 1 tier weapons
	}

	@Override
	public String desc() {
		return super.desc() + "\n\n" + Messages.get(this, "energy", chargePerHit, chargePerHit*2, charge, chargeCap);
	}

	@Override
	public int proc(Char attacker, Char defender, int damage ) {
		if (attacker instanceof Hero && !power) {
			charge += chargePerHit;
			if (charge >= chargeCap) {
				charge = chargeCap;
			}
		}
		if (attacker instanceof Hero && power) {
			ArrayList<Lightning.Arc> arcs = new ArrayList<>();
			ArrayList<Char> affected = new ArrayList<>();
			affected.clear();
			arcs.clear();

			Shocking.arc(hero, defender, 2, affected, arcs);

			affected.remove(defender); //defender isn't hurt by lightning
			for (Char ch : affected) {
				if (ch.alignment != hero.alignment) {
					ch.damage(Math.round(0.4f*damage), this);
				}
			}

			hero.sprite.parent.addToFront( new Lightning( arcs, null ) );
			Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
			charge -= chargeUsePerHit;
			if (charge < chargeUsePerHit) {
				power = false;
				GLog.w(Messages.get(this, "power_off"));
			}
		}
		Item.updateQuickslot();
		return super.proc( attacker, defender, damage );
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped( hero )) {
			actions.add(AC_POWER);
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);

		if (action.equals(AC_POWER)) {
			if (!isEquipped(hero)) {
				GLog.w(Messages.get(this, "not_equipped"));
			} else {
				if (charge > chargeUsePerHit) {
					if (power) {
						power = false;
						GLog.n(Messages.get(this, "power_off"));
						curUser.busy();
						Sample.INSTANCE.play(Assets.Sounds.UNLOCK, 2, 1.1f);
						curUser.sprite.operate(curUser.pos);
					} else {
						power = true;
						GLog.p(Messages.get(this, "power_on"));
						curUser.busy();
						Sample.INSTANCE.play(Assets.Sounds.CHARGEUP, 2, 1.1f);
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

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
		bundle.put(CHARGE_CAP, chargeCap);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
		chargeCap = bundle.getInt(CHARGE_CAP);
	}

	@Override
	public String status() {
		return Messages.format(TXT_STATUS, charge);
	}
}
