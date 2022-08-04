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

package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class Concentrate extends Buff {
	
	private int count = 0;
	private float duration = 0f;
	private final float maxDuration = 5f;

	@Override
	public int icon() {
		return BuffIndicator.MARK;
	}

	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}
	
	public void hit() {
		duration = maxDuration;
		count += Dungeon.hero.pointsInTalent(Talent.CONCENTRATE_SHOOTING);
		if (count >= 20*Dungeon.hero.pointsInTalent(Talent.CONCENTRATE_SHOOTING)) {
			count = 20*Dungeon.hero.pointsInTalent(Talent.CONCENTRATE_SHOOTING);
		}
		BuffIndicator.refreshHero(); //refresh the buff visually on-hit
	}

	@Override
	public float iconFadePercent() {
		return Math.max(0, (maxDuration - duration)/ maxDuration);
	}

	@Override
	public boolean act() {
		duration-=TICK;
		spend(TICK);
		if (duration <= 0) {
			detach();
		}
		return true;
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", Dungeon.hero.pointsInTalent(Talent.CONCENTRATE_SHOOTING), count, duration);
	}

	private static final String COUNT = "count";
	private static final String DURATION  = "duration";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(COUNT, count);
		bundle.put(DURATION, duration);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		count = bundle.getInt( COUNT );
		duration = bundle.getFloat( DURATION );
	}

}
