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

import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class TempleMazeRoom extends StandardRoom {

	@Override
	public int minWidth() {
		return Math.max(super.minWidth(), 7);
	}

	@Override
	public int minHeight() {
		return Math.max(super.minHeight(), 7);
	}

	@Override
	public float[] sizeCatProbs() {
		return new float[]{0, 1, 1};
	}

	@Override
	public void paint(Level level) {
//		Painter.fill(level, this, Terrain.WALL);
//		Painter.fill(level, this, 1, Terrain.GRASS);
//		Painter.fill(level, this, 2, Terrain.HIGH_GRASS);
//		for (int i = 0; i < 15; i ++){
//			Painter.set(level, random(2), Terrain.CHASM);
//		}
//		for (int i = 0; i < 10; i ++){
//			Painter.set(level, random(3), Terrain.CHASM);
//		}
//		for (int i = 0; i < 5; i ++){
//
//		}

		Painter.fill(level, this, Terrain.WALL);
		int layers = (Math.min(width(), height())-1)/2;
		for (int i = 1; i <= layers; i++){
			Painter.fill(level, this, i, (i % 2 == 0) ? Terrain.WALL : Terrain.HIGH_GRASS);
			if (i % 2 == 0) {
				Painter.set(level, randomBridge(i), Terrain.HIGH_GRASS);
			}
		}

		for (Door door : connected.values()) {
			door.set(Door.Type.REGULAR);
		}
	}

	public Point randomBridge(int m) {
		int x;
		int y;
		int n = m+1; //모서리에 다리가 생기는 것을 방지하기 위함
		switch (Random.Int(4)) {
			case 0: default:
				x = left + m;
				y = Random.IntRange( top + n, bottom - n );
				break;
			case 1:
				x = right - m;
				y = Random.IntRange( top + n, bottom - n );
				break;
			case 2:
				x = Random.IntRange( left + n, right - n );
				y = top + m;
				break;
			case 3:
				x = Random.IntRange( left + n, right - n );
				y = bottom - m;
				break;
		}

		return new Point(x, y);
	}

}
