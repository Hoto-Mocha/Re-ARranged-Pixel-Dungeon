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
import com.shatteredpixel.shatteredpixeldungeon.Bones;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ConfusionGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.CorrosiveGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ParalyticGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AscensionChallenge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Rebel;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.HandyBarricade;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Point;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;

public class LabsBossLevel extends Level {

	{
		color1 = 0x48763c;
		color2 = 0x59994a;
	}

	@Override
	public void playLevelMusic() {
		Music.INSTANCE.playTracks(
				new String[]{Assets.Music.HALLS_1, Assets.Music.HALLS_2, Assets.Music.HALLS_2},
				new float[]{1, 1, 0.5f},
				false);
	}

	private static int WIDTH = 33;
	private static int HEIGHT = 35;

	private static final Rect entry = new Rect(0, 27, 33, 6);
	private static final Rect arena = new Rect(0, 0, 33, 26);
	private static final Rect exitDoor = new Rect(16, 1, 16, 1);

	private static final int topDoor = 16 + 33;
	private static final int bottomDoor = 16 + (arena.bottom+1) * 33;
	private static final int bossPos = bottomDoor-11*33;

	private static boolean isCompleted = false;

	@Override
	public String tilesTex() {
		return Assets.Environment.TILES_LABS;
	}

	@Override
	public String waterTex() {
		return Assets.Environment.WATER_LABS;
	}

	@Override
	protected boolean build() {
		setSize(WIDTH, HEIGHT);

		transitions.add(new LevelTransition(this, pointToCell(new Point(16, 30)), LevelTransition.Type.REGULAR_ENTRANCE));

		transitions.add(new LevelTransition(this, topDoor, LevelTransition.Type.REGULAR_EXIT));

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
	private static final short L = Terrain.LOCKED_EXIT;

	private static short[] level = {
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, L, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, s, s, s, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, s, p, s, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, s, s, s, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, s, s, s, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, W, W, W, W, W, W,
			W, W, s, s, s, s, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, s, s, s, s, W, W,
			W, W, s, p, s, s, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, s, s, p, s, W, W,
			W, W, s, s, s, s, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, s, s, s, s, W, W,
			W, W, W, W, W, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, D, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, s, s, s, s, s, s, s, s, e, e, e, s, s, s, s, s, s, s, s, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, s, p, s, s, s, s, s, s, e, E, e, s, s, s, s, s, s, p, s, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, s, s, s, s, s, s, s, s, e, e, e, s, s, s, s, s, s, s, s, s, W, W, W, W, W, W,
			W, W, W, W, W, W, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W
	};

	private void buildLevel(){
		int pos = 0 + 0*width(); 								//시작점의 x, y 좌표, width()가 곱해져 있는 것이 y다.

		short[] levelTiles = level; 							//위에서 노가다 한 것을 하나의 변수로 만들고
		for (int i = 0; i < levelTiles.length; i++){ 			//0부터 위 노가다에 포함된 요소의 개수만큼 반복
			if (levelTiles[i] != n) map[pos] = levelTiles[i];	//요소를 n으로 설정한 것이 아닌 경우 위 노가다에 포함
			// 된 요소대로 맵 제작
			pos++; 												//만들고 나서 다음 칸으로 이동하기 위해 필요한 것. ex)벽 -> 벽일 경우 첫번째 벽이 pos고, 두번째 벽이 pos+1.
		}

//		Blob.seed(3+(36)*33, 12, ConfusionGasSeed.class, this);
//		Blob.seed(10+(36)*33, 12, ToxicGasSeed.class, this);
//		Blob.seed(17+(36)*33, 12, ParalyticGasSeed.class, this);
//		Blob.seed(24+(36)*33, 12, CorrosiveGasSeed.class, this);
	}

	@Override
	public void occupyCell(Char ch) {

		super.occupyCell(ch);

		boolean isRebelAlive = false;

		for (int i=0 ; i < Dungeon.level.length ; i++) {
			Char mob = Actor.findChar(i);
			if (mob instanceof Rebel) {
				isRebelAlive = true;
				break;
			}
		}

		if (map[bottomDoor] != Terrain.LOCKED_DOOR && ch.pos < bottomDoor && ch == Dungeon.hero && !isCompleted) {
			seal();
			return;
		}

		if (Dungeon.level.map[topDoor] == Terrain.LOCKED_EXIT && ch.pos < bottomDoor && ch == Dungeon.hero && !isRebelAlive) {
			seal(); //When the boss is not appeared
		}
	}

	@Override
	protected void createMobs() {
	}

	@Override
	protected void createItems() {
		drop( new HandyBarricade().quantity(5), pointToCell(new Point(8, 30)) );
		drop( new HandyBarricade().quantity(5), pointToCell(new Point(24, 30)) );

		Item item = Bones.get();
		if (item != null) {
			int pos;
			do {
				pos = randomRespawnCell(null);
			} while (pos == entrance());
			drop( item, pos ).setHauntedIfCursed().type = Heap.Type.REMAINS;
		}
	}

	public int randomCellPos() {
		Point[][] randomPoint = new Point[19][19];
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				randomPoint[i][j] = new Point(i+7, j+7);
			}
		}
		return pointToCell(randomPoint[Random.Int(19)][Random.Int(19)]);
	}

	@Override
	public int randomRespawnCell( Char ch ) {
		int cell;
		do {
			cell = entrance() + PathFinder.NEIGHBOURS8[Random.Int(8)];
		} while (!passable[cell]
				|| (Char.hasProp(ch, Char.Property.LARGE) && !openSpace[cell])
				|| Actor.findChar(cell) != null);
		return cell;
	}

	@Override
	public void seal() {
		super.seal();

		Rebel boss = new Rebel();
		boss.state = boss.WANDERING;
		boss.pos = bossPos;
		GameScene.add( boss );
		boss.beckon(Dungeon.hero.pos);

		if (heroFOV[boss.pos]) {
			boss.notice();
			boss.sprite.alpha( 0 );
			boss.sprite.parent.add( new AlphaTweener( boss.sprite, 1, 0.1f ) );
		}

		//moves intelligent allies with the hero, preferring closer pos to entrance door
		int doorPos = bottomDoor;
		Mob.holdAllies(this, doorPos);
		Mob.restoreAllies(this, Dungeon.hero.pos, doorPos);

		set(bottomDoor, Terrain.LOCKED_DOOR);
		GameScene.updateMap(bottomDoor);
		Dungeon.observe();
	}

	@Override
	public void unseal() {
		super.unseal();

		set(topDoor, Terrain.UNLOCKED_EXIT);
		set(bottomDoor, Terrain.DOOR);
		GameScene.updateMap(topDoor);
		CellEmitter.get(topDoor).burst( Speck.factory( Speck.WOOL ), 8 );
		GameScene.updateMap(bottomDoor);
		isCompleted = true;

		Dungeon.observe();
	}

	private static final String ISCOMPLETED = "iscompleted";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put(ISCOMPLETED, isCompleted);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		isCompleted = bundle.getBoolean( ISCOMPLETED );
	}

	@Override
	public boolean activateTransition(Hero hero, LevelTransition transition) {
		if (transition.type == LevelTransition.Type.REGULAR_ENTRANCE
				//ascension challenge only works on runs started on v1.3+
				&& Dungeon.initialVersion > ShatteredPixelDungeon.v1_2_3
				&& hero.belongings.getItem(Amulet.class) != null
				&& hero.buff(AscensionChallenge.class) == null) {

			Game.runOnRenderThread(new Callback() {
				@Override
				public void call() {
					GameScene.show( new WndOptions( new ItemSprite(ItemSpriteSheet.AMULET),
							Messages.get(Amulet.class, "ascent_title"),
							Messages.get(Amulet.class, "ascent_desc"),
							Messages.get(Amulet.class, "ascent_yes"),
							Messages.get(Amulet.class, "ascent_no")){
						@Override
						protected void onSelect(int index) {
							if (index == 0){
								Buff.affect(hero, AscensionChallenge.class);
								Statistics.highestAscent = 30;
								LabsBossLevel.super.activateTransition(hero, transition);
							}
						}
					} );
				}
			});
			return false;

		} else {
			return super.activateTransition(hero, transition);
		}
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
			case Terrain.LOCKED_EXIT:
				return Messages.get(LabsLevel.class, "locked_exit_name");
			case Terrain.UNLOCKED_EXIT:
				return Messages.get(LabsLevel.class, "unlocked_exit_name");
			case Terrain.EMPTY_SP:
				return Messages.get(LabsLevel.class, "empty_sp_name");
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
			case Terrain.EMPTY_SP:
				return Messages.get(LabsLevel.class, "empty_sp_desc");
			case Terrain.WALL_DECO:
				return Messages.get(LabsLevel.class, "wall_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(LabsLevel.class, "bookshelf_desc");
			case Terrain.STATUE:
				return Messages.get(LabsLevel.class, "statue_desc");
			case Terrain.LOCKED_EXIT:
				return Messages.get(LabsLevel.class, "locked_exit_desc");
			case Terrain.UNLOCKED_EXIT:
				return Messages.get(LabsLevel.class, "unlocked_exit_desc");
			default:
				return super.tileDesc( tile );
		}
	}

//	public static class ToxicGasSeed extends Blob {
//
//		@Override
//		protected void evolve() {
//			int cell;
//			ToxicGas gas = (ToxicGas) Dungeon.level.blobs.get(ToxicGas.class);
//			for (int i=area.top-1; i <= area.bottom; i++) {
//				for (int j = area.left-1; j <= area.right; j++) {
//					cell = j + i* Dungeon.level.width();
//					if (Dungeon.level.insideMap(cell)) {
//
//						off[cell] = cur[cell];
//						volume += off[cell];
//
//						if (gas == null || gas.volume == 0){
//							GameScene.add(Blob.seed(cell, 300, ToxicGas.class));
//						} else if (gas.cur[cell] <= 9*off[cell]){
//							GameScene.add(Blob.seed(cell, 300, ToxicGas.class));
//						}
//					}
//				}
//			}
//		}
//
//	}
//
//	public static class ParalyticGasSeed extends Blob {
//
//		@Override
//		protected void evolve() {
//			int cell;
//			ParalyticGas gas = (ParalyticGas) Dungeon.level.blobs.get(ParalyticGas.class);
//			for (int i=area.top-1; i <= area.bottom; i++) {
//				for (int j = area.left-1; j <= area.right; j++) {
//					cell = j + i* Dungeon.level.width();
//					if (Dungeon.level.insideMap(cell)) {
//
//						off[cell] = cur[cell];
//						volume += off[cell];
//
//						if (gas == null || gas.volume == 0){
//							GameScene.add(Blob.seed(cell, 300, ParalyticGas.class));
//						} else if (gas.cur[cell] <= 9*off[cell]){
//							GameScene.add(Blob.seed(cell, 300, ParalyticGas.class));
//						}
//					}
//				}
//			}
//		}
//
//	}
//
//	public static class CorrosiveGasSeed extends Blob {
//
//		@Override
//		protected void evolve() {
//			int cell;
//			CorrosiveGas gas = (CorrosiveGas) Dungeon.level.blobs.get(CorrosiveGas.class);
//			for (int i=area.top-1; i <= area.bottom; i++) {
//				for (int j = area.left-1; j <= area.right; j++) {
//					cell = j + i* Dungeon.level.width();
//					if (Dungeon.level.insideMap(cell)) {
//
//						off[cell] = cur[cell];
//						volume += off[cell];
//
//						if (gas == null || gas.volume == 0){
//							GameScene.add(Blob.seed(cell, 300, CorrosiveGas.class));
//						} else if (gas.cur[cell] <= 9*off[cell]){
//							GameScene.add(Blob.seed(cell, 300, CorrosiveGas.class));
//						}
//					}
//				}
//			}
//		}
//
//	}
//
//	public static class ConfusionGasSeed extends Blob {
//
//		@Override
//		protected void evolve() {
//			int cell;
//			ConfusionGas gas = (ConfusionGas) Dungeon.level.blobs.get(ConfusionGas.class);
//			for (int i=area.top-1; i <= area.bottom; i++) {
//				for (int j = area.left-1; j <= area.right; j++) {
//					cell = j + i* Dungeon.level.width();
//					if (Dungeon.level.insideMap(cell)) {
//
//						off[cell] = cur[cell];
//						volume += off[cell];
//
//						if (gas == null || gas.volume == 0){
//							GameScene.add(Blob.seed(cell, 300, ConfusionGas.class));
//						} else if (gas.cur[cell] <= 9*off[cell]){
//							GameScene.add(Blob.seed(cell, 300, ConfusionGas.class));
//						}
//					}
//				}
//			}
//		}
//
//	}
}