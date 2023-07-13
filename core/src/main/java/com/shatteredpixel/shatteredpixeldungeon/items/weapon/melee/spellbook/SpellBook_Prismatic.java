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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Awareness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpellBookCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfPrismaticLight;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class SpellBook_Prismatic extends SpellBook {

	{
		image = ItemSpriteSheet.PRISMATIC_SPELLBOOK;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.1f;

		tier = 3;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		float procChance = (buffedLvl()+1f)/(buffedLvl()+3f);
		if (Random.Float() < procChance) {
			Buff.affect(defender, Blindness.class, 2+buffedLvl());
		}
		if (defender.properties().contains(Char.Property.DEMONIC) || defender.properties().contains(Char.Property.UNDEAD)){
			defender.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10+buffedLvl() );
			Sample.INSTANCE.play(Assets.Sounds.BURNING);

			damage *= 1.3333f; //deals more damage to the demons and the undeads
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
		if (Dungeon.level.viewDistance < 6 ){
			Buff.prolong( curUser, Light.class, 30f+5*buffedLvl());
			Sample.INSTANCE.play(Assets.Sounds.BURNING);
		} else {
			if (buffedLvl() >= 10) {
				Buff.affect(hero, Awareness.class ,2f);
				GLog.p( Messages.get(SpellBook_Empty.class, "awareness") );
			} else {
				GLog.i( Messages.get(this, "nothing") );
			}
		}
		needAnimation = busy;
		if (needAnimation) {
			readAnimation();
		}
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{SpellBook_Empty.class, WandOfPrismaticLight.class};
			inQuantity = new int[]{1, 1};

			cost = 10;

			output = SpellBook_Prismatic.class;
			outQuantity = 1;
		}
	}
}
