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
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class Outlaw extends Buff {
	
	private int count = 0;
	private int maxCount = 5;

	@Override
	public int icon() {
		return BuffIndicator.BULLET;
	}
	
	@Override
	public void tintIcon(Image icon) {
		if (count < maxCount) {
			icon.hardlight(0xFFDB65);
		} else {
			icon.hardlight(0xFF3333);
		}
	}
	public int pos = -1;

	@Override
	public boolean act() {
		if (pos == -1) pos = target.pos;
		if (pos != target.pos) {
			detach();
		} else {
			spend(TICK);
		}
		return true;
	}

	public boolean isReady() {
		return maxCount-count <= 0;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}
	
	public void count() {
		if (count < maxCount) {
			count ++;
		} else {
			count = maxCount;
		}
		BuffIndicator.refreshHero(); //refresh the buff
	}

	@Override
	public void detach() {
		super.detach();
	}

	@Override
	public String desc() {
		if (count < maxCount) return Messages.get(this, "desc", maxCount-count);
		else return Messages.get(this, "desc_ready");
	}

	private static final String COUNT = "count";
	private static final String MAXCOUNT  = "maxCount";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(COUNT, count);
		bundle.put(MAXCOUNT, maxCount);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		count = bundle.getInt( COUNT );
		maxCount = bundle.getInt( MAXCOUNT );
	}
}
