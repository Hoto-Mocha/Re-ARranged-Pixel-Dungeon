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

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.ShockingBrew;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class ElectroScimitar extends MeleeWeapon {

	{
		image = ItemSpriteSheet.ELECTRO_SCIMITAR;
		hitSound = Assets.Sounds.HIT_SLASH;
		hitSoundPitch = 1.2f;

		tier = 4;
		DLY = 0.67f; //1.5x speed
		alchemy = true;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		if (Random.Int(10) <= Math.floor(hero.belongings.weapon.buffedLvl()/4f)) {
			Buff.affect(defender, Paralysis.class, 2f);
			defender.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
			defender.sprite.flash();
			Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
		}
		return super.proc( attacker, defender, damage );
	}

	@Override
	public int max(int lvl) {
		return  4*(tier+1) +    //base
				lvl*(tier+1);   //level scaling
	}

	@Override
	public float abilityChargeUse(Hero hero) {
		return 2*super.abilityChargeUse(hero);
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		beforeAbilityUsed(hero);
		Buff.prolong(hero, Scimitar.SwordDance.class, 5f); //5 turns as using the ability is instant
		hero.sprite.operate(hero.pos);
		hero.next();
		afterAbilityUsed(hero);
	}

	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{Scimitar.class, ShockingBrew.class, LiquidMetal.class};
			inQuantity = new int[]{1, 1, 60};

			cost = 10;

			output = ElectroScimitar.class;
			outQuantity = 1;
		}
	}

}
