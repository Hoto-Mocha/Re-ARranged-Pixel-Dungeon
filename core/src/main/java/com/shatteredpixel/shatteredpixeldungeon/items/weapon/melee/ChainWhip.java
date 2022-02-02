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
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class ChainWhip extends MeleeWeapon {

	{
		image = ItemSpriteSheet.CHAIN_WHIP;
		hitSound = Assets.Sounds.HIT_CRUSH;
		hitSoundPitch = 1f;

		tier = 5;
		RCH = 4;    //lots of extra reach
	}

	@Override
	public int max(int lvl) {
		return  3*(tier-1) +    //12 base, down from 30
				lvl*(tier-2);   //+3 per level, down from +6
	}

	//see Hero.onAttackComplete for additional effect
}
