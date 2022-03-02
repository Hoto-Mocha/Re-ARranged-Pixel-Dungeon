/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.TrueRunicBlade;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class Arcane extends Weapon.Enchantment {

	private static ItemSprite.Glowing TEAL = new ItemSprite.Glowing( 0x88EEFF );

	@Override
	public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
		int level = Math.max( 0, weapon.level() );

		// lvl 0 - 13%
		// lvl 1 - 22%
		// lvl 2 - 30%
		float procChance;
		if (weapon instanceof TrueRunicBlade) {
			procChance = 1;
		} else {
			procChance = (level+1f)/(level+8f) * procChanceMultiplier(attacker);
		}
		if (Random.Float() < procChance) {
			int maxShield = 10*(level+1); //max shielding amount
			int shielding = (attacker.buff(Barrier.class) != null) ? attacker.buff(Barrier.class).shielding() : 0; //current shielding
			int incShield = Math.min(Math.round(damage/5f), maxShield-shielding); //amount of shielding that will affect
			if (attacker.buff(Barrier.class) != null) {
				Buff.affect(attacker, Barrier.class).incShield(Math.round(incShield));
			} else {
				Buff.affect(attacker, Barrier.class).setShield(Math.round(incShield));
			}
			ScrollOfRecharging.charge(attacker);

		}

		return damage;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return TEAL;
	}

}
