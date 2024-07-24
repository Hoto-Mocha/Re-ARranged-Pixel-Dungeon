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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArmorEnhance;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WeaponEnhance;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfAugmentation;
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
		talentChance = 1/(float) Recipe.OUT_QUANTITY;
	}
	
	@Override
	protected void onCast(Hero hero) {
		Buff.affect(hero, WeaponEnhance.class).set(1+hero.lvl/10, 20f);
		Buff.affect(hero, ArmorEnhance.class).set(1+hero.lvl/10, 20f);

		hero.sprite.operate(hero.pos);
		Sample.INSTANCE.play( Assets.Sounds.EVOKE );
		CellEmitter.center( hero.pos ).burst( Speck.factory( Speck.STAR ), 7 );

		GLog.p( Messages.get(this, "empower") );
		
		detach( curUser.belongings.backpack );
		updateQuickslot();
		Invisibility.dispel();
		hero.spendAndNext( 1f );
	}

	@Override
	public int value() {
		//prices of ingredients, divided by output quantity
		return Math.round((40 + 30)/(float) Recipe.OUT_QUANTITY);
	}

	@Override
	public int energyVal() {
		return (int)(8 * (quantity/(float) Recipe.OUT_QUANTITY));
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		private static final int OUT_QUANTITY = 3;

		{
			inputs =  new Class[]{StoneOfAugmentation.class, LiquidMetal.class};
			inQuantity = new int[]{1, 20};

			cost = 3;

			output = UpgradeDust.class;
			outQuantity = 3;
		}

	}
}
