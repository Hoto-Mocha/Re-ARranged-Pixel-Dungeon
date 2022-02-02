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
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfIcyTouch;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class FrostScimitar extends MeleeWeapon {

	{
		image = ItemSpriteSheet.FROST_SCIMITAR;
		hitSound = Assets.Sounds.HIT_SLASH;
		hitSoundPitch = 1.2f;

		tier = 4;
		DLY = 0.8f; //1.25x speed
		//also affects chilling, see Hero.onAttackComplete
	}

	@Override
	public int max(int lvl) {
		return  4*(tier) +    //16 base
				lvl*(tier);   //scaling unchanged
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{Scimitar.class, ElixirOfIcyTouch.class, LiquidMetal.class};
			inQuantity = new int[]{1, 1, 40};

			cost = 5;

			output = FrostScimitar.class;
			outQuantity = 1;
		}
	}

}
