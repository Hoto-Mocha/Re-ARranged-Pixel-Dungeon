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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LargeSwordBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class LargeSword extends MeleeWeapon {

	{
		image = ItemSpriteSheet.LARGESWORD;
		hitSound = Assets.Sounds.HIT_SLASH;
		hitSoundPitch = 0.8f;

		tier = 5;
		DLY = 2f; //0.5x speed
	}

	@Override
	public int proc(Char attacker, Char defender, int damage ) {
		LargeSwordBuff buff = Dungeon.hero.buff(LargeSwordBuff.class);
		if (attacker instanceof Hero && buff != null) {
			damage *= buff.getDamageFactor();
			if (buff.getDamageFactor() > 2) {
				int cell = defender.pos;
				for (int k : PathFinder.NEIGHBOURS8){
					Char ch = Actor.findChar( cell+k );
					if (ch != null && ch.alignment != Char.Alignment.ALLY){
						ch.damage(Math.round(damage * 0.4f), Dungeon.hero);
					}
				}

				WandOfBlastWave.BlastWave.blast(cell);
				Sample.INSTANCE.play( Assets.Sounds.BLAST );
			}
			buff.detach();
		}
		return super.proc( attacker, defender, damage );
	}

	@Override
	public int max(int lvl) {
		return  Math.round(6.67f*(tier+1)) +    //40 base, up from 30
				lvl*Math.round(1.33f*(tier+1)); //+8 per level, up from +6
	}

}