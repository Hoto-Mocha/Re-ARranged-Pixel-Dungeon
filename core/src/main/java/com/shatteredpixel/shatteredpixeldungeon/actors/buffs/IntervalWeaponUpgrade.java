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

import static com.shatteredpixel.shatteredpixeldungeon.items.Item.updateQuickslot;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class IntervalWeaponUpgrade extends Buff {
	{
		type = buffType.POSITIVE;
	}

	private int boost;
	private float interval;

	public void levelUp() {
		if (boost < 3) {
			this.boost ++;
		}
		switch (boost) {
			case 1: default:
				this.interval = 50f;
				break;
			case 2:
				this.interval = 30f;
				break;
			case 3:
				this.interval = 10f;
				break;
		}
		spend(interval - cooldown());
	}

	public int boost(){
		return boost;
	}

	@Override
	public boolean act() {
		boost --;
		switch (boost) {
			case 1: default:
				this.interval = 50f;
				break;
			case 2:
				this.interval = 30f;
				break;
			case 3:
				this.interval = 10f;
				break;
		}
		if (boost > 0){
			spend( interval );
		} else {
			detach();
		}
		updateQuickslot();
		return true;
	}

	@Override
	public int icon() {
		return BuffIndicator.WEAPON;
	}

	@Override
	public void tintIcon(Image icon) {
		switch (boost) {
			case 1: default:
				icon.hardlight(0xFFFFFF);
				break;
			case 2:
				icon.hardlight(0xFFFF00);
				break;
			case 3:
				icon.hardlight(0xFF0000);
				break;
		}
	}

	@Override
	public float iconFadePercent() {
		return Math.max(0, (interval - visualcooldown()) / interval);
	}

	@Override
	public String iconTextDisplay() {
		return Integer.toString((int)visualcooldown());
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", boost, dispTurns(visualcooldown()));
	}

	private static final String BOOST	    = "boost";
	private static final String INTERVAL	= "interval";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( BOOST, boost );
		bundle.put( INTERVAL, interval );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		boost = bundle.getInt( BOOST );
		interval = bundle.getFloat(INTERVAL);
	}
}
