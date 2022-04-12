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

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.items.ArcaneResin;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorruption;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class UnholyBible extends MeleeWeapon {

	{
		image = ItemSpriteSheet.UNHOLY_BIBLE;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.1f;

		tier = 4;
		//also affects enemy lots of debuffs, see Hero.onAttackComplete
		alchemy = true;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		switch (Random.Int(15)) {
			case 0: case 1: default:
				Buff.affect( defender, Weakness.class, 3f );
				break;
			case 2: case 3:
				Buff.affect( defender, Vulnerable.class, 3f );
				break;
			case 4:
				Buff.affect( defender, Cripple.class, 3f );
				break;
			case 5:
				Buff.affect( defender, Blindness.class, 3f );
				break;
			case 6:
				Buff.affect( defender, Terror.class, 3f );
				break;
			case 7: case 8: case 9:
				Buff.affect( defender, Amok.class, 3f );
				break;
			case 10: case 11:
				Buff.affect( defender, Slow.class, 3f );
				break;
			case 12: case 13:
				Buff.affect( defender, Hex.class, 3f );
				break;
			case 14:
				Buff.affect( defender, Paralysis.class, 3f );
				break;
		}
		if (Random.Int(100) <= Math.min(buffedLvl(), 9)) {					//1% base, +1% per lvl, max 10%
			Buff.affect( defender, com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom.class);
		}
		return damage;
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
