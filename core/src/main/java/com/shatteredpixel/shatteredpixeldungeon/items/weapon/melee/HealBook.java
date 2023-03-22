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
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class HealBook extends MeleeWeapon {

	{
		image = ItemSpriteSheet.HEAL_BOOK;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.1f;

		tier = 1;
		bones = false;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		if (Random.Int(3) == 0) {
			int healAmt = 1;
			attacker.heal(healAmt);
		}
		return super.proc( attacker, defender, damage );
	}

	@Override
	public int max(int lvl) {
		return  Math.round(2.5f*(tier+1)) +     //5 base, down from 10
				lvl*Math.round(0.5f*(tier+1));  //+1 per level, down from +2
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		Bible.angelAbility(hero, 6, this);
	}

}
