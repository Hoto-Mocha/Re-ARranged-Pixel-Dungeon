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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.knight;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.EnergyParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;

public class HolyShield extends ArmorAbility {

	{
		baseChargeUse = 35f;
	}

	@Override
	protected void activate(ClassArmor armor, Hero hero, Integer target) {

		int shieldAmount = 25;
		if (hero.hasTalent(Talent.BUFFER_BARRIER)) {
			shieldAmount += 5*hero.pointsInTalent(Talent.BUFFER_BARRIER);
		}
		Buff.affect(hero, Barrier.class).setShield(shieldAmount);
		if (hero.hasTalent(Talent.HOLY_LIGHT)) {
			Buff.affect(hero, Light.class, 25*hero.pointsInTalent(Talent.HOLY_LIGHT));
		}
		if (hero.hasTalent(Talent.BLESSING)) {
			Buff.affect(hero, Bless.class, 5*hero.pointsInTalent(Talent.BLESSING));
		}
		hero.sprite.operate(hero.pos);
		Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
		Emitter e = hero.sprite.centerEmitter();
		if (e != null) e.burst( EnergyParticle.FACTORY, 15 );

		armor.charge -= chargeUse(hero);
		armor.updateQuickslot();
		hero.spendAndNext(Actor.TICK);
	}

	@Override
	public int icon() {
		return HeroIcon.HOLY_SHIELD;
	}

	@Override
	public Talent[] talents() {
		return new Talent[]{Talent.BUFFER_BARRIER, Talent.HOLY_LIGHT, Talent.BLESSING, Talent.HEROIC_ENERGY};
	}
}
