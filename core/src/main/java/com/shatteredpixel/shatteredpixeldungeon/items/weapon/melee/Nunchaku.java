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
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Nunchaku extends MeleeWeapon {

	{
		image = ItemSpriteSheet.NUNCHAKU;
		hitSound = Assets.Sounds.HIT_CRUSH;
		hitSoundPitch = 1f;

		tier = 2;
		DLY = 0.5f;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		if (Random.Int(10) == 0) {
			Buff.affect(defender, Cripple.class, 3f);
		}
		return super.proc( attacker, defender, damage );
	}

	@Override
	public int max(int lvl) {
		return  Math.round(2.5f*(tier+1)) +
				lvl*Math.round(0.5f*(tier+1));
	}

	@Override
	public float abilityChargeUse(Hero hero, Char target) {
		return 2*super.abilityChargeUse(hero, target);
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		beforeAbilityUsed(hero, null);
		Invisibility.dispel();
		Buff.affect(hero, Nunchaku.ParryTracker.class, Actor.TICK);
		hero.spendAndNext(Actor.TICK);
		hero.busy();
		afterAbilityUsed(hero);
	}

	public static class ParryTracker extends FlavourBuff {
		{ actPriority = HERO_PRIO+1;}

		public boolean  parried;
	}

	public static class RiposteTracker extends Buff{
		{ actPriority = VFX_PRIO;}

		public Char enemy;

		@Override
		public boolean act() {
			target.sprite.attack(enemy.pos, new Callback() {
				@Override
				public void call() {
					AttackIndicator.target(enemy);
					if (hero.attack(enemy, 0.5f, 0, Char.INFINITE_ACCURACY)){
						Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
					}

					next();
					if (!enemy.isAlive()){
						((MeleeWeapon)hero.belongings.attackingWeapon()).onAbilityKill(hero, enemy);
					} else {
						Buff.affect(enemy, Cripple.class, 5f);
					}
				}
			});
			detach();
			return false;
		}
	}

}
