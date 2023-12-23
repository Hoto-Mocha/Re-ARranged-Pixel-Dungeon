/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.text.DecimalFormat;

public class UpgradeDust extends Spell {
	
	{
		image = ItemSpriteSheet.UPGRADE_DUST;
	}
	
	@Override
	protected void onCast(Hero hero) {
		Buff.affect(hero, WeaponEnhance.class).set(1+hero.lvl/10, 30f);

		hero.sprite.operate(hero.pos);
		Sample.INSTANCE.play( Assets.Sounds.EVOKE );
		CellEmitter.center( hero.pos ).burst( Speck.factory( Speck.STAR ), 7 );

		GLog.p( Messages.get(this, "empower") );
		
		detach( curUser.belongings.backpack );
		updateQuickslot();
		Invisibility.dispel();
		hero.spendAndNext( 1f );
	}
	
	public static class WeaponEnhance extends Buff {
		{
			type = buffType.POSITIVE;
		}

		private int lvl = 0;
		private float duration = 0f;
		private float maxTime = 0f;

		public void set( int level, float duration ) {
			this.duration = duration+1f;
			maxTime = this.duration-1f;
			lvl = level;
		}

		@Override
		public void detach() {
			super.detach();
			Item.updateQuickslot();
		}

		@Override
		public int icon() {
			return BuffIndicator.UPGRADE;
		}

		@Override
		public void tintIcon(Image icon) {
			icon.hardlight(1, 1, 0);
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc", lvl, new DecimalFormat("#").format(duration));
		}

		@Override
		public float iconFadePercent() {
			return Math.max((maxTime - duration)/maxTime, 0);
		}

		@Override
		public boolean act() {
			duration-=1f;
			spend(1f);
			if (duration <= 0) {
				detach();
			}
			return true;
		}

		public int getLvl() {
			return lvl;
		}

		private static final String DURATION = "duration";
		private static final String MAXTIME = "maxTime";
		private static final String LVL = "lvl";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(DURATION, duration);
			bundle.put(MAXTIME, maxTime);
			bundle.put(LVL, lvl);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			duration = bundle.getFloat(DURATION);
			maxTime = bundle.getFloat(MAXTIME);
			lvl = bundle.getInt(LVL);
		}
	}

	@Override
	public int value() {
		//prices of ingredients, divided by output quantity
		return (40 + 40)/quantity;
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{LiquidMetal.class, ArcaneCatalyst.class};
			inQuantity = new int[]{40, 1};

			cost = 3;

			output = UpgradeDust.class;
			outQuantity = 2;
		}

	}
}
