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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class StimPack extends ArmorAbility {

	{
		baseChargeUse = 50f;
	}

	@Override
	protected void activate(ClassArmor armor, Hero hero, Integer target) {
		int damage = Math.round(hero.HT*(0.3f - 0.05f*(hero.pointsInTalent(Talent.BURDEN_RELIEF))));
		if (hero.HP <= damage) {
			GLog.w(Messages.get(this, "cannot_use"));
		} else {
			hero.damage(damage, hero);
			hero.sprite.operate(hero.pos);
			Sample.INSTANCE.play(Assets.Sounds.DRINK);
			int duration = 20;
			if (hero.hasTalent(Talent.LASTING_PACK)) {
				duration += 5*hero.pointsInTalent(Talent.LASTING_PACK);
			}
			Buff.prolong(hero, Adrenaline.class, duration);
			if (hero.hasTalent(Talent.TIME_STOP)) {
				Buff.affect(hero, Swiftthistle.TimeBubble.class).set(hero.pointsInTalent(Talent.TIME_STOP)+1);
			}
			armor.charge -= chargeUse(hero);
			armor.updateQuickslot();
			hero.spendAndNext(Actor.TICK);
		}
	}

	@Override
	public int icon() {
		return HeroIcon.STIMPACK;
	}

	@Override
	public Talent[] talents() {
		return new Talent[]{Talent.BURDEN_RELIEF, Talent.LASTING_PACK, Talent.TIME_STOP, Talent.HEROIC_ENERGY};
	}
}
