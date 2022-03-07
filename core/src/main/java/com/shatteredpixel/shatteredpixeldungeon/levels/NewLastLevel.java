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

package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.tiles.CustomTilemap;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTileSheet;
import com.watabou.noosa.Group;
import com.watabou.noosa.Tilemap;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.Arrays;

public class NewLastLevel extends Level {

	{
		color1 = 0x48763c;
		color2 = 0x59994a;
	}

	private static int WIDTH = 9;
	private static int HEIGHT = 34;

	@Override
	public String tilesTex() {
		return Assets.Environment.TILES_LABS;
	}

	@Override
	public String waterTex() {
		return Assets.Environment.WATER_LABS;
	}

	private static final int ROOM_TOP = 10;

	@Override
	protected boolean build() {
		exit = 4 + (4)*9;
		entrance = 4 + (30)*9;
		setSize(WIDTH, HEIGHT);

		//entrance room
		buildLevel();
		//arena room

		//customArenaVisuals = new LabArenaVisuals();
		//customArenaVisuals.setRect(0, 12, width(), 27);
		//customTiles.add(customArenaVisuals);

		return true;
	}

	private static final short n = -1; //used when a tile shouldn't be changed
	private static final short W = Terrain.WALL;
	private static final short e = Terrain.EMPTY;
	private static final short E = Terrain.ENTRANCE;
	private static final short p = Terrain.PEDESTAL;
	private static final short s = Terrain.EMPTY_SP;
	private static final short D = Terrain.DOOR;
	private static final short i = Terrain.CUSTOM_TILE_1;
	private static final short t = Terrain.CUSTOM_TILE_2;
	private static final short r = Terrain.CUSTOM_TILE_3;
	private static final short L = Terrain.LOCKED_EXIT;

	private static short[] level = {
			W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W,
			W, W, r, r, r, r, r, W, W,
			W, W, r, t, t, t, r, W, W,
			W, W, r, t, p, t, r, W, W,
			W, W, r, t, t, t, r, W, W,
			W, W, r, r, t, r, r, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, W, r, t, r, W, W, W,
			W, W, t, t, t, t, t, W, W,
			W, W, t, i, i, i, t, W, W,
			W, W, t, i, E, i, t, W, W,
			W, W, t, i, i, i, t, W, W,
			W, W, t, t, t, t, t, W, W,
			W, W, W, W, W, W, W, W, W
	};

	private void buildLevel(){
		int pos = 0 + 0*width(); 								//시작점의 x, y 좌표, width()가 곱해져 있는 것이 y다.

		short[] levelTiles = level; 							//위에서 노가다 한 것을 하나의 변수로 만들고
		for (int i = 0; i < levelTiles.length; i++){ 			//0부터 위 노가다에 포함된 요소의 개수만큼 반복
			if (levelTiles[i] != n) map[pos] = levelTiles[i];	//요소를 n으로 설정한 것이 아닌 경우 위 노가다에 포함
			// 된 요소대로 맵 제작
			pos++; 												//만들고 나서 다음 칸으로 이동하기 위해 필요한 것. ex)벽 -> 벽일 경우 첫번째 벽이 pos고, 두번째 벽이 pos+1.
		}
	}
	
	@Override
	public Mob createMob() {
		return null;
	}
	
	@Override
	protected void createMobs() {
	}

	public Actor addRespawner() {
		return null;
	}

	@Override
	protected void createItems() {
		drop( new Amulet(), exit );
	}

	@Override
	public int randomRespawnCell( Char ch ) {
		int cell;
		do {
			cell = entrance + PathFinder.NEIGHBOURS8[Random.Int(8)];
		} while (!passable[cell]
				|| (Char.hasProp(ch, Char.Property.LARGE) && !openSpace[cell])
				|| Actor.findChar(cell) != null);
		return cell;
	}

	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(LabsLevel.class, "water_name");
			case Terrain.WALL_DECO:
				return Messages.get(LabsLevel.class, "wall_deco_name");
			case Terrain.STATUE:
				return Messages.get(LabsLevel.class, "statue_name");
			case Terrain.CUSTOM_TILE_1:
				return Messages.get(LabsLevel.class, "custom_tile_1_name");
			case Terrain.CUSTOM_TILE_2:
				return Messages.get(LabsLevel.class, "custom_tile_2_name");
			case Terrain.CUSTOM_TILE_3:
				return Messages.get(LabsLevel.class, "custom_tile_3_name");
			case Terrain.LOCKED_EXIT:
				return Messages.get(LabsLevel.class, "locked_exit_name");
			case Terrain.UNLOCKED_EXIT:
				return Messages.get(LabsLevel.class, "unlocked_exit_name");
			default:
				return super.tileName( tile );
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.ENTRANCE:
				return Messages.get(LabsLevel.class, "entrance_desc");
			case Terrain.EXIT:
				return Messages.get(LabsLevel.class, "exit_desc");
			case Terrain.EMPTY_DECO:
				return Messages.get(LabsLevel.class, "empty_deco_desc");
			case Terrain.WALL_DECO:
				return Messages.get(LabsLevel.class, "wall_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(LabsLevel.class, "bookshelf_desc");
			case Terrain.STATUE:
				return Messages.get(LabsLevel.class, "statue_desc");
			case Terrain.CUSTOM_TILE_1:
				return Messages.get(LabsLevel.class, "custom_tile_1_desc");
			case Terrain.CUSTOM_TILE_2:
				return Messages.get(LabsLevel.class, "custom_tile_2_desc");
			case Terrain.CUSTOM_TILE_3:
				return Messages.get(LabsLevel.class, "custom_tile_3_desc");
			case Terrain.LOCKED_EXIT:
				return Messages.get(LabsLevel.class, "locked_exit_desc");
			case Terrain.UNLOCKED_EXIT:
				return Messages.get(LabsLevel.class, "unlocked_exit_desc");
			default:
				return super.tileDesc( tile );
		}
	}
}
