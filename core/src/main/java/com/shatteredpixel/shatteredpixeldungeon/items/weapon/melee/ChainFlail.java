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
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class ChainFlail extends MeleeWeapon {

	{
		image = ItemSpriteSheet.CHAIN_FLAIL;
		hitSound = Assets.Sounds.HIT_CRUSH;
		hitSoundPitch = 0.8f;

		tier = 6;
		RCH = 2;
		ACC = 0.8f; //0.8x accuracy
		//also cannot surprise attack, see Hero.canSurpriseAttack
	}

	@Override
	public int max(int lvl) {
		return  Math.round(7*(tier-1)) +        //35 base
				lvl*Math.round(1.6f*(tier-1));  //+8 per level
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{ChainWhip.class, Flail.class, LiquidMetal.class};
			inQuantity = new int[]{1, 1, 60};

			cost = 5;

			output = ChainFlail.class;
			outQuantity = 1;
		}
	}
}
