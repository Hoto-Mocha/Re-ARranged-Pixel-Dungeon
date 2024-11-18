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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.BlobImmunity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.HealingArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;

public class HealareaGenerator extends ArmorAbility {

	{
		baseChargeUse = 35f;
	}

	@Override
	public void activate(ClassArmor armor, Hero hero, Integer target) {
		Buff.affect(hero, HealGen.class).setup(20+5*hero.pointsInTalent(Talent.DURABLE_GEN));
		if (hero.hasTalent(Talent.SHIELD_GEN)) {
			Buff.prolong(hero, BlobImmunity.class, 10 * hero.pointsInTalent(Talent.SHIELD_GEN));
		}

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
		return new Talent[]{Talent.AREA_AMP, Talent.SHIELD_GEN, Talent.DURABLE_GEN, Talent.HEROIC_ENERGY};
	}

	public static class HealGen extends Buff {

		int left = 0;

		{
			type = buffType.POSITIVE;
		}

		public void setup( int duration ) {
			left = duration;
		}

		@Override
		public boolean act() {
			Buff.affect(target, HealingArea.class).setup(target.pos, 2, 1+Dungeon.hero.pointsInTalent(Talent.AREA_AMP), false);
			left--;
			BuffIndicator.refreshHero();
			if (left <= 0){
				detach();
			}
			spend(TICK);
			return true;
		}

		@Override
		public void detach() {
			super.detach();
		}
	}
}