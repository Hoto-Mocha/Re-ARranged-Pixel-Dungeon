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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ToxicImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DwarfKing;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PoisonParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.AlchemicalCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfDivineInspiration;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfMastery;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.StatusPane;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndHero;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

public class ElixirOfTalent extends Elixir {
	
	{
		image = ItemSpriteSheet.ELIXIR_TALENT;
	}
	
	@Override
	public void apply(Hero hero) {
		if (hero.buff(BonusTalentTracker.class) == null && hero.subClass != HeroSubClass.NONE && hero.lvl >= 20 && hero.armorAbility == null) {
			hero.STR -= 2;
			Buff.affect(hero, BonusTalentTracker.class);
			Sample.INSTANCE.playDelayed(Assets.Sounds.LEVELUP, 0.2f, 0.7f, 1.2f);
			Sample.INSTANCE.playDelayed(Assets.Sounds.LEVELUP, 0.4f, 0.7f, 1.2f);
			Sample.INSTANCE.playDelayed(Assets.Sounds.LEVELUP, 0.6f, 0.7f, 1.2f);
			Sample.INSTANCE.playDelayed(Assets.Sounds.LEVELUP, 0.8f, 0.7f, 1.2f);
			hero.sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "str_decrease") );
			GLog.p(Messages.get(this, "bonus"));
			GameScene.showlevelUpStars();
		} else {
			hero.STR += 2;
			if (!Dungeon.isChallenged(Challenges.SUPERMAN)){
				hero.HT += 10;
				hero.sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "str_ht") );
			} else {
				hero.sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "str") );
			}
			GLog.p( Messages.get(this, "msg_2") );
		}

		Badges.validateStrengthAttained();
		curUser.busy();
		curUser.sprite.operate(curUser.pos);

		curUser.spendAndNext(1f);

		boolean unspentTalents = false;
		for (int i = 1; i <= Dungeon.hero.talents.size(); i++){
			if (Dungeon.hero.talentPointsAvailable(i) > 0){
				unspentTalents = true;
				break;
			}
		}
		if (unspentTalents){
			StatusPane.talentBlink = 10f;
			WndHero.lastIdx = 1;
		}



		Sample.INSTANCE.play( Assets.Sounds.DRINK );
	}
	
	@Override
	protected int splashColor() {
		return 0xFFFDFA31;
	}
	
	@Override
	public int value() {
		//prices of ingredients
		return quantity * (50 + 70 + 40);
	}

	public static class BonusTalentTracker extends Buff {
		{
			type = buffType.POSITIVE;
			revivePersists = true;
		}
	}
	
	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {
		
		{
			inputs =  new Class[]{PotionOfStrength.class, PotionOfMastery.class, AlchemicalCatalyst.class};
			inQuantity = new int[]{1, 1, 1};
			
			cost = 10;
			
			output = ElixirOfTalent.class;
			outQuantity = 1;
		}
		
	}
	
}
