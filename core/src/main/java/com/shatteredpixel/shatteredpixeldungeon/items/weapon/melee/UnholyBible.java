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
import com.shatteredpixel.shatteredpixeldungeon.items.ArcaneResin;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorruption;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class UnholyBible extends MeleeWeapon {

	{
		image = ItemSpriteSheet.UNHOLY_BIBLE;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.1f;

		tier = 4;
		//also affects enemy lots of debuffs, see Hero.onAttackComplete
	}

	@Override
	public int max(int lvl) {
		return  3*(tier) +    		//12 base
				lvl*(tier-1);     	//+3 per level
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{Bible.class, WandOfCorruption.class, ArcaneResin.class};
			inQuantity = new int[]{1, 1, 2};

			cost = 10;

			output = UnholyBible.class;
			outQuantity = 1;
		}
	}

}
