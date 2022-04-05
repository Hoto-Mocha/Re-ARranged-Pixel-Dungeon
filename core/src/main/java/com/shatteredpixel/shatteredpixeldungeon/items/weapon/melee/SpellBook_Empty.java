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
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArcaneArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Awareness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EvasiveMove;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpellBookCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.ArcaneResin;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfReload;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SpellBook_Empty extends MeleeWeapon {

	public static final String AC_READ		= "READ";

	{
		defaultAction = AC_READ;
		usesTargeting = false;

		image = ItemSpriteSheet.EMPTY_SPELLBOOK;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.1f;

		tier = 3;
		alchemy = true;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_READ);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_READ)) {
			if (hero.buff(SpellBookCoolDown.class) != null) {
				GLog.w( Messages.get(SpellBook_Empty.class, "fail") );
			} else if (!isIdentified()) {
				GLog.w( Messages.get(SpellBook_Empty.class, "need_id") );
			} else {
				switch(Random.Int( 10 )){
					case 0: default:
						Buff.affect(hero, Recharging.class, 20f+buffedLvl());
						GLog.p( Messages.get(this, "recharge") );
						break;
					case 1:
						Buff.affect(hero, ArtifactRecharge.class).set(10f+buffedLvl());
						GLog.p( Messages.get(this, "artifact") );
						break;
					case 2:
						Buff.affect(hero, Bless.class, 20f+buffedLvl());
						GLog.p( Messages.get(this, "bless") );
						break;
					case 3:
						Buff.affect(hero, Adrenaline.class, 10f+buffedLvl());
						GLog.p( Messages.get(this, "adrenaline") );
						break;
					case 4:
						Buff.affect(hero, Barrier.class).setShield(Dungeon.depth+buffedLvl());
						GLog.p( Messages.get(this, "barrier") );
						break;
					case 5:
						Buff.affect(hero, Barkskin.class).set(Dungeon.depth/2+buffedLvl(), 1);
						GLog.p( Messages.get(this, "barkskin") );
						break;
					case 6:
						Buff.affect(hero, Invisibility.class, 10f+buffedLvl());
						GLog.p( Messages.get(this, "invisibility") );
						break;
					case 7:
						Buff.affect(hero, ArcaneArmor.class).set(Dungeon.depth/2+buffedLvl(), 1);
						GLog.p( Messages.get(this, "arcane_armor") );
						break;
					case 8:
						Buff.affect(hero, Awareness.class, 2f);
						GLog.p( Messages.get(this, "awareness") );
						break;
					case 9:
						Buff.affect(hero, EvasiveMove.class, 3f);
						GLog.p( Messages.get(this, "evasive_move") );
						break;
				}
				Invisibility.dispel();
				curUser.spend( Actor.TICK );
				curUser.busy();
				((HeroSprite)curUser.sprite).read();
				Sample.INSTANCE.play(Assets.Sounds.READ);
				Buff.affect(hero, SpellBookCoolDown.class, Math.max(200f-10f*buffedLvl(), 100f));
			}
		}
	}

	@Override
	public int max(int lvl) {
		return  Math.round(2.5f*(tier+1)) +     //10 base, down from 20
				lvl*(tier-1);                   //+2 per level, down from +4
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{WandOfMagicMissile.class, ArcaneResin.class};
			inQuantity = new int[]{1, 2};

			cost = 5;

			output = SpellBook_Empty.class;
			outQuantity = 1;
		}
	}

}
