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

package com.shatteredpixel.shatteredpixeldungeon.items.stones;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.shatteredpixeldungeon.effects.Enchanting;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistol_Energy;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Dagger_Energy;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gloves_Energy;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Saber_Energy;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WornKatana_Energy;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WornShortsword_Energy;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class StoneOfEnchantment extends InventoryStone {
	
	{
		preferredBag = Belongings.Backpack.class;
		image = ItemSpriteSheet.STONE_ENCHANT;

		unique = true;
	}

	@Override
	protected boolean usableOnItem(Item item) {
		return ScrollOfEnchantment.enchantable(item);
	}
	
	@Override
	protected void onItemSelected(Item item) {
		
		if (item instanceof Weapon) {
			
			((Weapon)item).enchant();
			
		} else {
			
			((Armor)item).inscribe();
			
		}
		
		curUser.sprite.emitter().start( Speck.factory( Speck.LIGHT ), 0.1f, 5 );
		Enchanting.show( curUser, item );
		
		if (item instanceof Weapon) {
			GLog.p(Messages.get(this, "weapon"));
		} else {
			GLog.p(Messages.get(this, "armor"));
		}

		item.fix();
		
		useAnimation();
		
	}
	
	@Override
	public int value() {
		return 30 * quantity;
	}

	@Override
	public int energyVal() {
		return 4 * quantity;
	}



	public static class Recipe1 extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{WornShortsword_Energy.class};
			inQuantity = new int[]{1};

			cost = 15;

			output = StoneOfEnchantment.class;
			outQuantity = 2;
		}
	}

	public static class Recipe2 extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{Dagger_Energy.class};
			inQuantity = new int[]{1};

			cost = 15;

			output = StoneOfEnchantment.class;
			outQuantity = 2;
		}
	}

	public static class Recipe3 extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{Gloves_Energy.class};
			inQuantity = new int[]{1};

			cost = 15;

			output = StoneOfEnchantment.class;
			outQuantity = 2;
		}
	}

	public static class Recipe4 extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{CrudePistol_Energy.class};
			inQuantity = new int[]{1};

			cost = 15;

			output = StoneOfEnchantment.class;
			outQuantity = 2;
		}
	}

	public static class Recipe5 extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{WornKatana_Energy.class};
			inQuantity = new int[]{1};

			cost = 15;

			output = StoneOfEnchantment.class;
			outQuantity = 2;
		}
	}

	public static class Recipe6 extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{Saber_Energy.class};
			inQuantity = new int[]{1};

			cost = 15;

			output = StoneOfEnchantment.class;
			outQuantity = 2;
		}
	}
}
