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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Stunning extends Weapon.Enchantment {
	
	private static Glowing YELLOW = new Glowing( 0xCCAA44 );
	
	@Override
	public int proc( Weapon weapon, Char attacker, Char defender, int damage ) {
		int level = Math.max( 0, weapon.level() );

		// lvl 0 - 5% * enemy's remaining HP rate
		// lvl 1 - 10% * enemy's remaining HP rate
		// lvl 2 - 14% * enemy's remaining HP rate
		float procChance;
		procChance = (level+1f)/(level+20f) * (defender.HP/(float)defender.HT) * procChanceMultiplier(attacker);

		float powerMulti = Math.max(1f, procChance);

		if (Random.Float() < procChance) {
			Buff.prolong( defender, Paralysis.class, 1f * powerMulti );
			defender.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 12 );
		}

		return damage;
	}
	
	@Override
	public Glowing glowing() {
		return YELLOW;
	}
}
