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

package com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs;

import static com.shatteredpixel.shatteredpixeldungeon.effects.Speck.HEALING;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Pasty;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.AlchemicalCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class ElixirOfHealth extends Elixir {
	
	{
		image = ItemSpriteSheet.ELIXIR_HEALTH;
	}
	
	@Override
	public void apply(Hero hero) {
		if (Dungeon.isChallenged(Challenges.SUPERMAN) || hero.buff(ElixirOfHealthHTBoost.class) != null) {
			hero.STR++;
			hero.onSTRGained();
			Buff.affect(hero, Hunger.class).satisfy(Hunger.STARVING);
			Talent.onFoodEaten(hero, Hunger.STARVING, this);
			GLog.p(Messages.get(this, "food"));
			hero.sprite.showStatus( CharSprite.POSITIVE, Messages.get(PotionOfStrength.class, "msg_1") );
		} else {
			hero.sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "max_health") );
			Buff.affect(hero, ElixirOfHealthHTBoost.class);
			hero.updateHT( true );
			Sample.INSTANCE.play( Assets.Sounds.DRINK );
			hero.sprite.emitter().burst(Speck.factory(HEALING), 2);
		}
	}
	
	@Override
	protected int splashColor() {
		return 0xFFFF002A;
	}
	
	@Override
	public int value() {
		//prices of ingredients
		return quantity * (50 + 40 + 20);
	}
	
	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {
		
		{
			inputs =  new Class[]{PotionOfStrength.class, AlchemicalCatalyst.class, Pasty.class};
			inQuantity = new int[]{1, 1, 1};
			
			cost = 8;
			
			output = ElixirOfHealth.class;
			outQuantity = 1;
		}
		
	}

	public static class ElixirOfHealthHTBoost extends Buff {

		{
			type = buffType.POSITIVE;
		}

		public int boost(){
			return Math.round((20 + 5 * (((Hero)target).lvl-1))/20f);
		}
	}
}
