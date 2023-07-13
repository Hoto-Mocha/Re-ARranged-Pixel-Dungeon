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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSight;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpellBookCoolDown;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfDisintegration;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class SpellBook_Disintegration extends SpellBook {

	{
		image = ItemSpriteSheet.DISTRIBUTION_SPELLBOOK;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.1f;

		tier = 3;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		Buff.affect(defender, VisionBuff.class, 3+buffedLvl());
		Buff.append(Dungeon.hero, TalismanOfForesight.CharAwareness.class, 3+buffedLvl()).charID = defender.id();
		return super.proc( attacker, defender, damage );
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		return actions;
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
		Buff.affect(hero, ReachBuff.class).set(4+buffedLvl(), buffedLvl());
		if (buffedLvl() >= 10) {
			Buff.affect(hero, MagicalSight.class, 10f);
		}
		Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
		Emitter e = hero.sprite.centerEmitter();
		if (e != null) e.burst(ShadowParticle.CURSE, 15);

		if (needAnimation) {
			readAnimation();
		}
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{SpellBook_Empty.class, WandOfDisintegration.class};
			inQuantity = new int[]{1, 1};

			cost = 10;

			output = SpellBook_Disintegration.class;
			outQuantity = 1;
		}
	}

	public static class VisionBuff extends FlavourBuff {}

	public static class ReachBuff extends Buff {
		private int maxDuration;
		private int left;
		private int upgrade;

		public int icon() {
			return BuffIndicator.WEAPON;
		}

		public void tintIcon(Image icon) {
			icon.hardlight(0x5A00B2);
		}

		public float iconFadePercent() {
			return Math.max(0, (maxDuration - left) / maxDuration);
		}

		public void set(int duration, int lvl) {
			maxDuration = duration;
			left = maxDuration;
			upgrade = lvl;
			if (upgrade > 9) {
				upgrade = 9;
			}
		}

		public int getUpgrade() {
			return upgrade;
		}

		private static final String MAX_DURATION = "maxDuration";
		private static final String LEFT = "left";
		private static final String UPGRADE = "upgrade";
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put( MAX_DURATION, maxDuration );
			bundle.put( LEFT, left );
			bundle.put( UPGRADE, upgrade );
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			maxDuration = bundle.getInt( MAX_DURATION );
			left = bundle.getInt( LEFT );
			upgrade = bundle.getInt( UPGRADE );
		}

		@Override
		public String iconTextDisplay() {
			return Integer.toString(left);
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc", 1+upgrade, left);
		}

		@Override
		public boolean act() {
			left--;
			if (left <= 0) {
				detach();
			}
			spend(TICK);
			return true;
		}
	}
}
