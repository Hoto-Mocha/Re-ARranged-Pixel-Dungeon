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
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EvasiveMove;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PrismaticGuard;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpellBookCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.MirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.PrismaticImage;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfWarding;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SpellBook_Warding extends MeleeWeapon {

	public static final String AC_READ		= "READ";

	{
		defaultAction = AC_READ;

		image = ItemSpriteSheet.WARDING_SPELLBOOK;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.1f;

		tier = 3;
		alchemy = true;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		float procChance = (buffedLvl()+1f)/(buffedLvl()+3f);
		if (Random.Float() < procChance) {
			boolean found = false;
			for (Mob m : Dungeon.level.mobs.toArray(new Mob[0])){
				if (m instanceof PrismaticImage){ //if the prismatic image is existing in the floor
					found = true;
					if (m.HP < m.HT) {
						m.HP = Math.min(m.HP+(int)(damage/2), m.HT); //heals the prismatic image
						m.sprite.emitter().burst(Speck.factory(Speck.HEALING), 4);
						m.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( Math.min((int)(damage/2), m.HT-m.HP) ) );
					}
				}
			}

			if (!found) {
				if (hero.buff(PrismaticGuard.class) != null) {
					Buff.affect(hero, PrismaticGuard.class).extend( (int)(damage/2) ); //heals the buff's hp
				} else {
					Buff.affect(hero, PrismaticGuard.class).set( (int)(damage/2) ); //affects a new buff to hero
				}
			}
		}
		return super.proc( attacker, defender, damage );
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_READ);
		return actions;
	}

	private static final int NIMAGES	= 3;

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_READ)) {
			if (hero.buff(SpellBookCoolDown.class) != null) {
				GLog.w( Messages.get(SpellBook_Empty.class, "fail") );
			} else if (!isIdentified()) {
				GLog.w( Messages.get(SpellBook_Empty.class, "need_id") );
			} else {
				spawnImages(curUser, NIMAGES);
				Buff.affect(hero, SpellBookCoolDown.class, Math.max(100f-5*buffedLvl(), 50f));
				Invisibility.dispel();
				curUser.spend( Actor.TICK );
				curUser.busy();
				((HeroSprite)curUser.sprite).read();
				Sample.INSTANCE.play(Assets.Sounds.READ);
			}
		}
	}

	public void spawnImages( Hero hero, int nImages ){

		ArrayList<Integer> respawnPoints = new ArrayList<>();

		for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
			int p = hero.pos + PathFinder.NEIGHBOURS8[i];
			if (Actor.findChar( p ) == null && Dungeon.level.passable[p]) {
				respawnPoints.add( p );
			}
		}

		while (nImages > 0 && respawnPoints.size() > 0) {
			int index = Random.index( respawnPoints );

			MirrorImage mob = new MirrorImage();
			mob.duplicate( hero );
			Buff.affect(mob, EvasiveMove.class, (buffedLvl() >= 10) ? 4f : 3f);
			Buff.affect(mob, Haste.class, (buffedLvl() >= 10) ? 4f : 3f);
			if(buffedLvl() >= 10) {
				Buff.affect(mob, Barkskin.class).set(this.buffedLvl(), 1);
			}
			GameScene.add( mob );
			ScrollOfTeleportation.appear( mob, respawnPoints.get( index ) );

			respawnPoints.remove( index );
			nImages--;
		}
	}

	@Override
	public int max(int lvl) {
		return  3*(tier+1) +     //10 base, down from 20
				lvl*(tier+1);                   //+2 per level, down from +4
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{SpellBook_Empty.class, WandOfWarding.class};
			inQuantity = new int[]{1, 1};

			cost = 10;

			output = SpellBook_Warding.class;
			outQuantity = 1;
		}
	}
}
