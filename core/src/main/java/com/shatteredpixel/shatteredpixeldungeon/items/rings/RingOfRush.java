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

package com.shatteredpixel.shatteredpixeldungeon.items.rings;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Flurry;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.samurai.ShadowBlade;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.text.DecimalFormat;

public class RingOfRush extends Ring {

	{
		icon = ItemSpriteSheet.Icons.RING_RUSH;
	}

	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats", new DecimalFormat("#.##").format(100f * (Math.pow(1.3f, soloBuffedBonus()) - 1f)), new DecimalFormat("#.##").format(100f * (1f - Math.max(Math.pow(0.85f, soloBuffedBonus()), 0.1f))));
		} else {
			return Messages.get(this, "typical_stats", new DecimalFormat("#.##").format(30f), new DecimalFormat("#.##").format(15f));
		}
	}

	@Override
	protected RingBuff buff( ) {
		return new Rush();
	}
	
	public static float rushSpeedMultiplier(Char target ){
		return (float)Math.pow(1.3, getBuffedBonus(target, Rush.class));
	}

	public static float damageMultiplier(Char target ){
		return Math.max((float)Math.pow(0.85, getBuffedBonus(target, Rush.class)), 0.1f);
	}

	public class Rush extends RingBuff {
	}
}
