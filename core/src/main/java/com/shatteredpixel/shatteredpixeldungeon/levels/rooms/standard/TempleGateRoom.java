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

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;

public class TempleGateRoom extends StandardRoom {

	@Override
	public int minWidth() { return 8; }
	public int maxWidth() { return 12; }

	@Override
	public int minHeight() { return 8; }
	public int maxHeight() { return 12; }

	@Override
	public void paint(Level level) {
		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1 , Terrain.EMPTY );
		
		for (Door door : connected.values()) {
			door.set( Door.Type.REGULAR );
		}

		Point c = center();

		Painter.fill( level, c.x-1, c.y-1, 3, 2, Terrain.WALL );
		Painter.fill( level, c.x, c.y, 1, 1, Terrain.EMPTY );
		Painter.fill( level, c.x-1, c.y+1, 3, 1, Terrain.EMPTY_SP );


		int entranceCell = level.pointToCell(c);

		level.transitions.add(new LevelTransition(level,
				entranceCell,
				LevelTransition.Type.BRANCH_EXIT,
				Dungeon.depth,
				Dungeon.branch + 2,
				LevelTransition.Type.BRANCH_ENTRANCE));
		Painter.set(level, entranceCell, Terrain.UNLOCKED_EXIT);
	}
}
