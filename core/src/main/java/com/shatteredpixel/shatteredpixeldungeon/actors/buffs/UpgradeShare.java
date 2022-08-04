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

package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class UpgradeShare extends Buff {

	{
		type = buffType.POSITIVE;
	}
	
	private int level = 0;
	private float duration = 0f;
	private float maxDuration = 0f;

	private static final String LEVEL = "level";
	private static final String DURATION  = "duration";
	private static final String MAXDURATION  = "maxDuration";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(LEVEL, level);
		bundle.put(DURATION, duration);
		bundle.put(MAXDURATION, maxDuration);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		level = bundle.getInt( LEVEL );
		duration = bundle.getFloat( DURATION );
		maxDuration = bundle.getFloat( MAXDURATION );
	}

	public int level(){
		return level;
	}

	public void set( int upgrade, float time ){
		level = upgrade;
		duration = time;
		maxDuration = duration;
	}
	
	@Override
	public int icon() {
		return BuffIndicator.UPGRADE;
	}

	@Override
	public void tintIcon(Image icon) {
		icon.hardlight(0x00D800);
	}

	@Override
	public float iconFadePercent() {
		return Math.max(0, (maxDuration - duration)/ maxDuration);
	}
	
	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", level, level, duration);
	}
	
	@Override
	public boolean act() {
		duration-=TICK;
		spend(TICK);
		if (duration <= 0) {
			detach();
			Item.updateQuickslot();
		}
		return true;
	}
}
