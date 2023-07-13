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
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blizzard;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Freezing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FrostImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpellBookCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class SpellBook_Frost extends SpellBook {

	{
		image = ItemSpriteSheet.FROST_SPELLBOOK;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.1f;

		tier = 3;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		float procChance = (buffedLvl()+1f)/(buffedLvl()+4f);
		if (Random.Float() < procChance) {
			//adds 3 turns of chill per proc, with a cap of 6 turns
			float durationToAdd = 3f;
			Chill existing = defender.buff(Chill.class);
			if (existing != null){
				durationToAdd = Math.min(durationToAdd, 6f-existing.cooldown());
			}

			Buff.affect( defender, Chill.class, durationToAdd );
			Splash.at( defender.sprite.center(), 0xFFB2D6FF, 5);
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
			pathFinder = PathFinder.NEIGHBOURS25;
		} else {
			pathFinder = PathFinder.NEIGHBOURS9;
		}

		for (int i : pathFinder) {
			int c = hero.pos + i;
			if (Dungeon.level.map[c] != Terrain.WALL && Dungeon.level.heroFOV[c]) {
				if (Dungeon.level.pit[c]) {
					GameScene.add(Blob.seed(c, 2, Freezing.class));
				} else {
					if (Dungeon.level.water[c]) {
						GameScene.add(Blob.seed(c, 20+buffedLvl(), Freezing.class));
					} else {
						GameScene.add(Blob.seed(c, 5+buffedLvl(), Freezing.class));
					}
					if (buffedLvl() >= 10) {
						GameScene.add(Blob.seed(c, 50 + 10 * buffedLvl(), Blizzard.class));
					}
				}
				Splash.at( c, 0xFFB2D6FF, 5);
			}
		}
		Sample.INSTANCE.play(Assets.Sounds.SHATTER);
		Buff.affect(hero, FrostImbue.class, Math.min(10+2*buffedLvl(), 50));

		if (needAnimation) {
			readAnimation();
		}
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{SpellBook_Empty.class, WandOfFrost.class};
			inQuantity = new int[]{1, 1};

			cost = 10;

			output = SpellBook_Frost.class;
			outQuantity = 1;
		}
	}
}
