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

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class ArmorEmpower extends Buff {

	{
		type = buffType.POSITIVE;
	}

	private int lvl;
	private float time;
	private float maxTime = 5;

	public void set( int level ) {
		time = maxTime;
		lvl = level;
	}

	public void set( int level, float duration ) {
		time = duration;
		lvl = level;
	}

	@Override
	public void detach() {
		super.detach();
		Item.updateQuickslot();
	}

	@Override
	public int icon() {
		return BuffIndicator.UPGRADE;
	}

	@Override
	public void tintIcon(Image icon) {
		icon.hardlight(0, 0, 1);
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", lvl, time);
	}

	@Override
	public float iconFadePercent() {
		return (maxTime - time)/maxTime;
	}

	@Override
	public boolean act() {
		time-=TICK;
		spend(TICK);
		if (time <= 0) {
			detach();
		}
		return true;
	}

	public int getLvl() {
		return lvl;
	}

	private static final String TIME = "time";
	private static final String MAXTIME = "maxTime";
	private static final String LVL = "lvl";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(TIME, time);
		bundle.put(MAXTIME, maxTime);
		bundle.put(LVL, lvl);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		time = bundle.getInt(TIME);
		maxTime = bundle.getInt(MAXTIME);
		lvl = bundle.getInt(LVL);
	}
}
