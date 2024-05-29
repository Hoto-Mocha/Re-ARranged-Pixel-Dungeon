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
import com.watabou.utils.PathFinder;
import com.watabou.utils.Point;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;

public class TempleCenterItemRoom extends PatchRoom {

	@Override
	public int minWidth() { return sizeCat.minDim+1; }
	public int minHeight() {
		return sizeCat.minDim+1;
	}

	@Override
	public float[] sizeCatProbs() {
		return new float[]{0, 3, 1};
	}

	//cannot roll even numbers
	@Override
	public Rect resize(int w, int h) {
		super.resize(w, h);
		if (width() % 2 == 0) right--;
		if (height() % 2 == 0) bottom--;
		return this;
	}

	@Override
	protected float fill() {
		return 0.5f;
	}

	@Override
	protected int clustering() {
		return 5;
	}

	@Override
	protected boolean ensurePath() {
		return false;
	}

	@Override
	protected boolean cleanEdges() {
		return false;
	}

	@Override
	public void paint(Level level) {
		Painter.fill( level, this, Terrain.WALL );

		Painter.fill( level, this, 1, Terrain.EMPTY );

		for (Door door : connected.values()) {
			door.set( Door.Type.REGULAR );
			if (door.x == left || door.x == right){
				Painter.drawInside(level, this, door, width()/2, Terrain.EMPTY);
			} else {
				Painter.drawInside(level, this, door, height()/2, Terrain.EMPTY);
			}
		}

		Painter.fill( level, this, 3 , Terrain.CHASM );

		Point start = new Point(left + width()/2, top + 3);
		Point end = new Point(left + width()/2, bottom - 3);
		Painter.drawLine(level, start, end, Terrain.EMPTY_SP);

		start.set(left+3, top + height()/2);
		end.set(right-3, top + height()/2);
		Painter.drawLine(level, start, end, Terrain.EMPTY_SP);

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

		Point center = center();
		Painter.fill( level, center.x-1, center.y-1, 3, 3, Terrain.STATUE_SP );
		Painter.set( level, center, Terrain.PEDESTAL );
		level.drop(i, level.pointToCell(center)).type = Heap.Type.CHEST;

		setupPatch(level);
	}

//	@Override
//	public void paint(Level level) {
//		Painter.fill( level, this, Terrain.WALL );
//		Painter.fill( level, this, 1 , Terrain.EMPTY );
//		Painter.fill( level, this, 2 , Terrain.CHASM );
//
//
//		for (Door door : connected.values()) {
//			door.set( Door.Type.REGULAR );
//		}
//
//		Item i = Random.oneOf(new ScrollOfTransmutation(),
//								new PotionOfExperience(),
//								new StoneOfEnchantment(),
//								Generator.random(Generator.Category.SCROLL),
//								Generator.random(Generator.Category.POTION),
//								Generator.random(Generator.Category.STONE),
//								null);
//
//		if ( i == null ) {
//			i = new Gold().random();
//		}
//
//		Point c = center();
//
//		int centerCell = level.pointToCell(c);
//		Painter.set(level, centerCell, Terrain.PEDESTAL);
//		for (int j : PathFinder.NEIGHBOURS8) {
//			int cell = centerCell + j;
//			Painter.set(level, cell, Terrain.BOOKSHELF);
//		}
//		level.drop(i, centerCell).type = Heap.Type.CHEST;
//	}
}
