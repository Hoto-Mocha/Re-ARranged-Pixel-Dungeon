/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard;

import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class TempleCenterItemRoom extends StandardRoom {

	@Override
	public float[] sizeCatProbs() {
		return new float[]{0, 0, 1};
	}

	@Override
	public int minWidth() { return 7; }
	public int maxWidth() { return 13; }

	@Override
	public int minHeight() { return minWidth(); }
	public int maxHeight() { return maxWidth(); }

	@Override
	public void paint(Level level) {
		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1 , Terrain.EMPTY );
		Painter.fill( level, this, 2 , Terrain.CHASM );

		
		for (Door door : connected.values()) {
			door.set( Door.Type.REGULAR );
		}

		Item i = Random.oneOf(new ScrollOfTransmutation(),
								new PotionOfExperience(),
								new StoneOfEnchantment(),
								Generator.random(Generator.Category.SCROLL),
								Generator.random(Generator.Category.POTION),
								Generator.random(Generator.Category.STONE),
								null);

		if ( i == null ) {
			i = new Gold().random();
		}

		Point c = center();

		int centerCell = level.pointToCell(c);
		Painter.set(level, centerCell, Terrain.PEDESTAL);
		level.drop(i, centerCell).type = Heap.Type.CHEST;
	}
}
