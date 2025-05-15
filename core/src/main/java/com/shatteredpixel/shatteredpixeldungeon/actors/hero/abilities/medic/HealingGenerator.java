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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.medic;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;

public class HealingGenerator extends ArmorAbility {

	{
		baseChargeUse = 35f;
	}

	@Override
	public void activate(ClassArmor armor, Hero hero, Integer target) {
		Buff.affect(hero, RegenBuff.class, RegenBuff.DURATION + 5*hero.pointsInTalent(Talent.DURABLE_GEN));

		hero.sprite.operate(hero.pos);
		hero.spendAndNext(Actor.TICK);

		armor.charge -= chargeUse(hero);
		armor.updateQuickslot();
		Invisibility.dispel();
	}

	@Override
	public int icon() {
		return HeroIcon.HEAL_GENERATOR;
	}

	@Override
	public Talent[] talents() {
		return new Talent[]{Talent.HEALING_AMP, Talent.SHIELD_GEN, Talent.DURABLE_GEN, Talent.HEROIC_ENERGY};
	}

	public static class RegenBuff extends FlavourBuff {
		public static final float DURATION	= 20f;

		{
			type = buffType.POSITIVE;
			announced = true;
		}

		@Override
		public int icon() {
			return BuffIndicator.REGEN;
		}

		@Override
		public float iconFadePercent() {
			return Math.max(0, (DURATION - visualcooldown()) / DURATION);
		}

		public void attackProc() {
			if (!(target instanceof Hero)) {
				detach();
				return;
			}

			Hero hero = (Hero) target;
			int affected = 0;
			for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
				if (mob.alignment == Char.Alignment.ALLY) {
					int healAmt = Math.max((2*hero.pointsInTalent(Talent.HEALING_AMP) + 10)-2*Dungeon.level.distance(hero.pos, mob.pos), 0); //기본 회복량은 10이나, 아군이 최소 1타일은 떨어져 있기 때문에 실질적인 초기 회복량은 8이 된다.
					if (healAmt > 0 && mob.HP < mob.HT) { //최대 체력인 아군은 회복시키지 않으며, 적용된 아군 숫자에도 포함시키지 않음
						mob.heal(healAmt);
						affected++;
					}
				}
			} //heals nearby enemies per every attack
			
			if (affected > 0 && hero.hasTalent(Talent.SHIELD_GEN)) { //방어막 적용 메커니즘
				affected = Math.min(affected, 8); //최대 8체까지만 적용
				for (int i = 0; i < affected; i++) {
					Barrier barrier = Buff.affect(hero, Barrier.class);
					if (barrier.shielding()+hero.pointsInTalent(Talent.SHIELD_GEN) > Math.round(hero.HT*0.8f)) { //실드가 최대 체력의 80%를 넘어갈 경우 방어막 양을 최대 체력의 80%로 설정
						barrier.setShield(Math.round(hero.HT*0.8f));
					} else {
						barrier.incShield(hero.pointsInTalent(Talent.SHIELD_GEN));
					}
				}
			}
		}
	}
}