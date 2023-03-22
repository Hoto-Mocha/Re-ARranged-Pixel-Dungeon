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
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class ChainFlail extends MeleeWeapon {

	{
		image = ItemSpriteSheet.CHAIN_FLAIL;
		hitSound = Assets.Sounds.HIT_CRUSH;
		hitSoundPitch = 0.8f;

		tier = 6;
		RCH = 2;
		ACC = 0.8f; //0.8x accuracy
		//also cannot surprise attack, see Hero.canSurpriseAttack
		alchemy = true;
	}

	@Override
	public int max(int lvl) {
		return  Math.round(7*(tier)) +        //42 base
				lvl*(tier+3);  //+9 per level
	}

	private static float spinBonus = 1f;

	@Override
	public float accuracyFactor(Char owner, Char target) {
		Flail.SpinAbilityTracker spin = owner.buff(Flail.SpinAbilityTracker.class);
		if (spin != null) {
			//have to handle this in an actor tied to the regular attack =S
			Actor.add(new Actor() {
				{ actPriority = VFX_PRIO; }
				@Override
				protected boolean act() {
					if (owner instanceof Hero && !target.isAlive()){
						onAbilityKill((Hero)owner);
					}
					Actor.remove(this);
					return true;
				}
			});
			//we detach and calculate bonus here in case the attack misses
			spin.detach();
			spinBonus = 1f + 0.2f*spin.spins;
			if (spinBonus == 1.6f){
				return Float.POSITIVE_INFINITY;
			} else {
				return super.accuracyFactor(owner, target);
			}
		} else {
			spinBonus = 1f;
			return super.accuracyFactor(owner, target);
		}
	}

	public float abilityChargeUse( Hero hero ) {
		if (Dungeon.hero.buff(Flail.SpinAbilityTracker.class) != null){
			return 0;
		} else {
			return 2*super.abilityChargeUse(hero);
		}
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {

		beforeAbilityUsed(hero);
		Flail.SpinAbilityTracker spin = hero.buff(Flail.SpinAbilityTracker.class);

		if (spin == null){
			spin = Buff.affect(hero, Flail.SpinAbilityTracker.class, 3f);
		}

		if (spin.spins < 3){
			spin.spins++;
			Buff.prolong(hero, Flail.SpinAbilityTracker.class, 3f);
			Sample.INSTANCE.play(Assets.Sounds.CHAINS, 1, 1, 0.9f + 0.1f*spin.spins);
			hero.sprite.operate(hero.pos);
			hero.spendAndNext(Actor.TICK);
			BuffIndicator.refreshHero();
		} else {
			GLog.w(Messages.get(this, "spin_warn"));
		}
		afterAbilityUsed(hero);
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{ChainWhip.class, Flail.class, LiquidMetal.class};
			inQuantity = new int[]{1, 1, 60};

			cost = 5;

			output = ChainFlail.class;
			outQuantity = 1;
		}
	}
}
