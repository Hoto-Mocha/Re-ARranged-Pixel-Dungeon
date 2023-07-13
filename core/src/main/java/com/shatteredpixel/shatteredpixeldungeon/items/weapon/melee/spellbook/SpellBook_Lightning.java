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

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Electricity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.BlobImmunity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpellBookCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Lightning;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLightning;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Shocking;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SpellBook_Lightning extends SpellBook {

	{
		image = ItemSpriteSheet.LIGHTNING_SPELLBOOK;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.1f;

		tier = 3;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		ArrayList<Lightning.Arc> arcs = new ArrayList<>();
		ArrayList<Char> affected = new ArrayList<>();
		float procChance = (buffedLvl()+1f)/(buffedLvl()+4f);
		if (Random.Float() < procChance) {
			affected.clear();
			arcs.clear();

			Shocking.arc(hero, defender, 2, affected, arcs);

			affected.remove(defender); //defender isn't hurt by lightning
			for (Char ch : affected) {
				if (ch.alignment != hero.alignment) {
					ch.damage(Math.round(damage * 0.4f), this);
				}
			}

			hero.sprite.parent.addToFront( new Lightning( arcs, null ) );
			Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
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
					GameScene.add(Blob.seed(c, 1, Electricity.class));
				} else {
					GameScene.add(Blob.seed(c, 5+buffedLvl(), Electricity.class));
				}
			}
		}
		Buff.affect(hero, BlobImmunity.class, 10+2*buffedLvl());
		Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);

		if (needAnimation) {
			readAnimation();
		}
	}

	private CellSelector.Listener spell = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer cell ) {
			if (cell != null) {
				if (buffedLvl() >= 10) {
					boolean infov = false;
					for (int n : PathFinder.NEIGHBOURS9) {
						int c = cell + n;
						if (Dungeon.level.map[c] != Terrain.WALL && Dungeon.level.heroFOV[c]) {
							if (Dungeon.level.pit[c]) {
								GameScene.add(Blob.seed(c, 2, Electricity.class));
							} else {
								GameScene.add(Blob.seed(c, 5+buffedLvl(), Electricity.class));
							}
							infov = true;
						}
					}
					if (!infov) {
						GLog.w( Messages.get(SpellBook_Lightning.this, "cannot_cast"));
					}
				} else {
					if (Dungeon.level.map[cell] != Terrain.WALL && Dungeon.level.heroFOV[cell]) {
						if (Dungeon.level.pit[cell]) {
							GameScene.add(Blob.seed(cell, 2, Electricity.class));
						} else {
							GameScene.add(Blob.seed(cell, 5+buffedLvl(), Electricity.class));
						}
					} else {
						GLog.w( Messages.get(SpellBook_Lightning.this, "cannot_cast"));
					}
				}
				Sample.INSTANCE.play(Assets.Sounds.LIGHTNING);

			}
		}
		@Override
		public String prompt() {
			return Messages.get(SpiritBow.class, "prompt");
		}
	};

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{SpellBook_Empty.class, WandOfLightning.class};
			inQuantity = new int[]{1, 1};

			cost = 10;

			output = SpellBook_Lightning.class;
			outQuantity = 1;
		}
	}

}