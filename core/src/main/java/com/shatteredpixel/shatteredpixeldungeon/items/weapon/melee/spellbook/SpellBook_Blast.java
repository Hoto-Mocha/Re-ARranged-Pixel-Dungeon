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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.spellbook;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpellBookCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class SpellBook_Blast extends SpellBook {

	{
		image = ItemSpriteSheet.BLAST_SPELLBOOK;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.1f;

		tier = 3;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		float procChance = (buffedLvl()+1f)/(buffedLvl()+5f);
		if (Random.Float() < procChance) {
			Buff.affect(defender, Paralysis.class, (buffedLvl() >= 10) ? 1f : 2f);
		}
		return super.proc( attacker, defender, damage );
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_READ)) {
			if (hero.buff(SpellBookCoolDown.class) != null) {
				return;
			} else if (!isIdentified()) {
				return;
			}
			readEffect(hero, true);
		}
	}

	@Override
	public void readEffect(Hero hero, boolean busy) {
		needAnimation = busy;

		int[] pathFinder;
		if (buffedLvl() >= 10) {
			pathFinder = PathFinder.NEIGHBOURS24;
		} else {
			pathFinder = PathFinder.NEIGHBOURS8;
		}

		for (int i : pathFinder) {
			Char ch = Actor.findChar(hero.pos+i);
			if (ch != null) {
				//trace a ballistica to our target (which will also extend past them
				Ballistica trajectory = new Ballistica(hero.pos, ch.pos, Ballistica.STOP_TARGET);
				//trim it to just be the part that goes past them
				trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size()-1), Ballistica.PROJECTILE);
				//knock them back along that ballistica
				if (buffedLvl() >= 10) {
					WandOfBlastWave.throwChar(ch, trajectory, 3 + buffedLvl(), true, true, hero.getClass());
				} else {
					WandOfBlastWave.throwChar(ch, trajectory, 2 + buffedLvl() / 2, true, true, hero.getClass());
				}
			}
		}
		Sample.INSTANCE.play( Assets.Sounds.BLAST );
		WandOfBlastWave.BlastWave.blast(hero.pos);

		if (needAnimation) {
			readAnimation();
		}
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{SpellBook_Empty.class, WandOfBlastWave.class};
			inQuantity = new int[]{1, 1};

			cost = 10;

			output = SpellBook_Blast.class;
			outQuantity = 1;
		}
	}

}
