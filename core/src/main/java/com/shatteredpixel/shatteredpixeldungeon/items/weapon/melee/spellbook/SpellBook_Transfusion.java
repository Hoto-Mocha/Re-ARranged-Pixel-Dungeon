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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AllyBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpellBookCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfTransfusion;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class SpellBook_Transfusion extends SpellBook {

	{
		image = ItemSpriteSheet.TRANSFUSION_SPELLBOOK;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.1f;

		tier = 3;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		//chance to heal scales from 5%-30% based on missing HP
		float missingPercent = (attacker.HT - attacker.HP) / (float)attacker.HT;
		float procChance = 0.05f + (0.25f+0.01f*buffedLvl())*missingPercent;
		if (Random.Float() < procChance) {

			//heals for 40% of damage dealt
			int healAmt = Math.round(damage * 0.4f);
			attacker.heal(healAmt);
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
			Mob target = null;
			Char ch = Actor.findChar(c);
			if (ch != null && ch.alignment != Char.Alignment.ALLY && ch instanceof Mob){
				target = (Mob)ch;
			}
			if (target != null) {
				if (buffedLvl() >= 10 && Random.Int(50) < buffedLvl() && !target.isImmune(ScrollOfSirensSong.Enthralled.class)) {
					AllyBuff.affectAndLoot(target, curUser, ScrollOfSirensSong.Enthralled.class); //makes target to ally for (20+2*buffedlvl())% chance
				} else {
					Buff.affect( target, Charm.class, Charm.DURATION+2*buffedLvl() ).object = curUser.id();
				}
				target.sprite.centerEmitter().burst( Speck.factory( Speck.HEART ), 10 );
			}
			CellEmitter.get(c).burst(Speck.factory( Speck.HEART ), 1);
		}
		Sample.INSTANCE.play(Assets.Sounds.CHARMS);

		if (needAnimation) {
			readAnimation();
		}
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{SpellBook_Empty.class, WandOfTransfusion.class};
			inQuantity = new int[]{1, 1};

			cost = 10;

			output = SpellBook_Transfusion.class;
			outQuantity = 1;
		}
	}
}
