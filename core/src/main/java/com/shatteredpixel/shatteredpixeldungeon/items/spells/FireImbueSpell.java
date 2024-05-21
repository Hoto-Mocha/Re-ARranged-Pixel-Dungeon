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

package com.shatteredpixel.shatteredpixeldungeon.items.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FireImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfDragonsBlood;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Embers;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;

public class FireImbueSpell extends Spell {
	
	{
		image = ItemSpriteSheet.FIRE_IMBUE;

		talentChance = 0;

		unique = true;
		bones = false;
	}
	
	@Override
	protected void onCast(Hero hero) {
		if (hero.buff(ImbueCooldown.class) == null) {
			if (Dungeon.isChallenged(Challenges.PYRO)) {
				Buff.affect(hero, FireImbue.class).set(10f/2);
			} else {
				Buff.affect(hero, FireImbue.class).set(10f);
			}
			Buff.affect(hero, ImbueCooldown.class, ImbueCooldown.DURATION);
			hero.sprite.operate(hero.pos);
			Sample.INSTANCE.play( Assets.Sounds.BURNING );
			hero.sprite.emitter().burst(FlameParticle.FACTORY, 10);

			GLog.p(Messages.get(this, "imbue"));

			updateQuickslot();
			hero.spendAndNext( 1f );
		} else {
			GLog.w(Messages.get(this, "cooldown"));
		}
	}

	public static class ImbueCooldown extends FlavourBuff {
		{
			type = buffType.NEUTRAL;
			announced = false;
		}

		public static final float DURATION	= 200f;

		@Override
		public int icon() {
			return BuffIndicator.TIME;
		}

		@Override
		public void tintIcon(Image icon) {
			icon.hardlight(0xFF0000);
		}

		@Override
		public float iconFadePercent() {
			return Math.max(0, (DURATION - visualcooldown()) / DURATION);
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc", dispTurns());
		}
	}
	
	@Override
	public int value() {
		return 0;
	}
	
	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {
		
		{
			inputs =  new Class[]{ElixirOfDragonsBlood.class, Embers.class};
			inQuantity = new int[]{1, 1};
			
			cost = 18;
			
			output = FireImbueSpell.class;
			outQuantity = 1;
		}
		
	}
}
