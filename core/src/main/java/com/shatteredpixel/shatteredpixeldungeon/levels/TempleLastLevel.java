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
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Bones;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LostInventory;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Brute;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Eye;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Guard;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Monk;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Shaman;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NPC;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.changer.OldAmulet;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.CrystalKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.GoldenKey;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MobSprite;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;

import java.util.ArrayList;

public class TempleLastLevel extends Level {

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

	private static int WIDTH = 31;
	private static int HEIGHT = 31;

	private static final Rect entry = new Rect(0, 27, 33, 6);
	private static final Rect arena = new Rect(0, 0, 33, 26);
	private static final Rect exitDoor = new Rect(16, 1, 16, 1);

	@Override
	public String tilesTex() {
		return Assets.Environment.TILES_TEMPLE;
	}

	@Override
	public String waterTex() {
		return Assets.Environment.WATER_TEMPLE;
	}

	@Override
	protected boolean build() {
		setSize(WIDTH, HEIGHT);

		transitions.add(new LevelTransition(this, 15 + WIDTH*15, LevelTransition.Type.REGULAR_ENTRANCE));

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
	private static final short C = Terrain.CRYSTAL_DOOR;
	private static final short B = Terrain.STATUE_SP;
	private static final short A = Terrain.ALCHEMY;
	private static final short H = Terrain.EMPTY_WELL;

	private static short[] level = {
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, p, e, s, s, B, p, W, W, W, W, W, W, B, s, s, s, B, W, W, W, W, W, W, p, B, s, s, e, p, W,
			W, e, e, B, s, B, B, p, W, W, W, W, W, s, e, e, e, s, W, W, W, W, W, p, B, B, s, B, e, e, W,
			W, s, B, B, s, s, B, B, p, W, W, W, W, s, e, p, e, s, W, W, W, W, p, B, B, s, s, B, B, s, W,
			W, s, s, s, s, s, s, B, B, p, W, W, W, s, e, e, e, s, W, W, W, p, B, B, s, s, s, s, s, s, W,
			W, B, B, s, s, s, s, s, B, B, W, W, W, B, s, s, s, B, W, W, W, B, B, s, s, s, s, s, B, B, W,
			W, p, B, B, s, s, s, s, s, s, W, W, W, W, W, C, W, W, W, W, W, s, s, s, s, s, s, B, B, p, W,
			W, W, p, B, B, s, s, s, s, s, W, W, W, W, W, e, W, W, W, W, W, s, s, s, s, s, B, B, p, W, W,
			W, W, W, p, B, B, s, s, e, e, D, e, s, s, W, C, W, s, s, e, D, e, e, s, s, B, B, p, W, W, W,
			W, W, W, W, p, B, s, s, e, W, W, e, s, s, W, e, W, s, s, e, W, W, e, s, s, B, p, W, W, W, W,
			W, W, W, W, W, W, W, W, D, W, e, e, s, s, W, C, W, s, s, e, e, W, D, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, e, e, e, e, s, s, W, e, W, s, s, e, e, e, e, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, s, s, s, s, s, s, W, C, W, s, s, s, s, s, s, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, s, s, s, s, s, s, e, e, e, s, s, s, s, s, s, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, s, s, H, s, s, s, e, E, e, s, s, s, H, s, s, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, s, s, s, s, s, s, e, e, e, s, s, s, s, s, s, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, e, e, e, e, s, s, s, s, s, s, s, e, e, e, e, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, D, W, e, e, s, s, s, H, s, s, s, e, e, W, D, W, W, W, W, W, W, W, W,
			W, W, W, W, p, B, s, s, e, W, W, e, s, s, s, s, s, s, s, e, W, W, e, s, s, B, p, W, W, W, W,
			W, W, W, p, B, B, s, s, e, e, D, e, s, s, s, s, s, s, s, e, D, e, e, s, s, B, B, p, W, W, W,
			W, W, p, B, B, s, s, s, s, s, W, W, W, W, W, D, W, W, W, W, W, s, s, s, s, s, B, B, p, W, W,
			W, p, B, B, s, s, s, s, s, s, W, W, W, s, s, s, s, s, W, W, W, s, s, s, s, s, s, B, B, p, W,
			W, B, B, s, s, s, s, s, B, B, W, W, W, s, s, s, s, s, W, W, W, B, B, s, s, s, s, s, B, B, W,
			W, s, s, s, s, s, s, B, B, p, W, W, W, s, s, A, s, s, W, W, W, p, B, B, s, s, s, s, s, s, W,
			W, s, B, B, s, s, B, B, p, W, W, W, W, s, s, s, s, s, W, W, W, W, p, B, B, s, s, B, B, s, W,
			W, e, e, B, s, B, B, p, W, W, W, W, W, s, s, s, s, s, W, W, W, W, W, p, B, B, s, B, e, e, W,
			W, p, e, s, s, B, p, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, p, B, s, s, e, p, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W
	};

	private void buildLevel(){
		int pos = 0 + 0*width(); 								//시작점의 x, y 좌표, width()가 곱해져 있는 것이 y다.
		short[] levelTiles = level; 							//위에서 노가다 한 것을 하나의 변수로 만들고
		ArrayList<Integer> sentryPos = new ArrayList<>();
		ArrayList<Integer> spawnerPos = new ArrayList<>();
		for (short levelTile : levelTiles) {            		//0부터 위 노가다에 포함된 요소의 개수만큼 반복
			if (levelTile != n) map[pos] = levelTile;    		//요소를 n으로 설정한 것이 아닌 경우 위 노가다에 포함된 요소대로 맵 제작

			if (map[pos] == p) {
				if (!(pos == 15+WIDTH*3
						|| pos == 1+WIDTH*1
						|| pos == 1+WIDTH*29
						|| pos == 29+WIDTH*1
						|| pos == 29+WIDTH*29)){
					sentryPos.add(pos);
				}
			}
			if (map[pos] == H) {
				spawnerPos.add(pos);
			}

			pos++;
		}
		for (int sentryCell : sentryPos) {
			Sentry sentry = new Sentry();
			sentry.pos = sentryCell;
			sentry.initialChargeDelay = sentry.curChargeDelay = 3f + 0.1f;
			this.mobs.add( sentry );
		}
		for (int sentryCell : spawnerPos) {
			Spawner spawner = new Spawner();
			spawner.pos = sentryCell;
			spawner.spawnCooldown = 20f;
			this.mobs.add( spawner );
		}
	}

	@Override
	protected void createMobs() {
	}

	@Override
	protected void createItems() {
		drop( new CrystalKey(20, 2), 1+WIDTH*1 ).type = Heap.Type.LOCKED_CHEST;
		drop( new CrystalKey(20, 2), 1+WIDTH*29 ).type = Heap.Type.LOCKED_CHEST;
		drop( new CrystalKey(20, 2), 29+WIDTH*1 ).type = Heap.Type.LOCKED_CHEST;
		drop( new CrystalKey(20, 2), 29+WIDTH*29 ).type = Heap.Type.LOCKED_CHEST;
		drop( new GoldenKey(20, 2), 8+WIDTH*8 );
		drop( new GoldenKey(20, 2), 8+WIDTH*22 );
		drop( new GoldenKey(20, 2), 22+WIDTH*8 );
		drop( new GoldenKey(20, 2), 22+WIDTH*22 );
		drop( new OldAmulet(), 15+WIDTH*3);

		Item item = Bones.get();
		if (item != null) {
			int pos;
			do {
				pos = randomRespawnCell(null);
			} while (pos == entrance());
			drop( item, pos ).setHauntedIfCursed().type = Heap.Type.REMAINS;
		}
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

	public static class Sentry extends NPC {

		{
			spriteClass = SentrySprite.class;

			properties.add(Property.IMMOVABLE);
		}

		private float initialChargeDelay;
		private float curChargeDelay;

		@Override
		protected boolean act() {
			if (fieldOfView == null || fieldOfView.length != Dungeon.level.length()){
				fieldOfView = new boolean[Dungeon.level.length()];
			}
			Dungeon.level.updateFieldOfView( this, fieldOfView );

			if (properties().contains(Property.IMMOVABLE)){
				throwItems();
			}

			if (Dungeon.hero != null){
				if (fieldOfView[Dungeon.hero.pos]
						&& Dungeon.level.map[Dungeon.hero.pos] == Terrain.EMPTY_SP
						&& Dungeon.hero.buff(LostInventory.class) == null){

					if (curChargeDelay > 0.001f){ //helps prevent rounding errors
						if (curChargeDelay == initialChargeDelay) {
							((SentrySprite) sprite).charge();
						}
						curChargeDelay -= Dungeon.hero.cooldown();
						//pity mechanic so mistaps don't get people instakilled
						if (Dungeon.hero.cooldown() >= 0.34f){
							Dungeon.hero.interrupt();
						}
					}

					if (curChargeDelay <= .001f){
						curChargeDelay = 1f;
						sprite.zap(Dungeon.hero.pos);
						((SentrySprite) sprite).charge();
					}

					spend(Dungeon.hero.cooldown());
					return true;

				} else {
					curChargeDelay = initialChargeDelay;
					sprite.idle();
				}

				spend(Dungeon.hero.cooldown());
			} else {
				spend(1f);
			}
			return true;
		}

		public void onZapComplete(){
			if (hit(this, Dungeon.hero, true)) {
				Dungeon.hero.damage(Random.IntRange(1, 3), new Eye.DeathGaze());
				if (!Dungeon.hero.isAlive()) {
					Badges.validateDeathFromEnemyMagic();
					Dungeon.fail(this);
					GLog.n(Messages.capitalize(Messages.get(Char.class, "kill", name())));
				}
			} else {
				Dungeon.hero.sprite.showStatus( CharSprite.NEUTRAL,  Dungeon.hero.defenseVerb() );
			}
		}

		@Override
		public int attackSkill(Char target) {
			return 20 + Dungeon.depth * 2;
		}

		@Override
		public int defenseSkill( Char enemy ) {
			return INFINITE_EVASION;
		}

		@Override
		public void damage( int dmg, Object src ) {
			//do nothing
		}

		@Override
		public boolean add( Buff buff ) {
			return false;
		}

		@Override
		public boolean reset() {
			return true;
		}

		@Override
		public boolean interact(Char c) {
			return true;
		}

		private static final String INITIAL_DELAY = "initial_delay";
		private static final String CUR_DELAY = "cur_delay";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(INITIAL_DELAY, initialChargeDelay);
			bundle.put(CUR_DELAY, curChargeDelay);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			initialChargeDelay = bundle.getFloat(INITIAL_DELAY);
			curChargeDelay = bundle.getFloat(CUR_DELAY);
		}
	}

	public static class SentrySprite extends MobSprite {

		private Animation charging;
		private Emitter chargeParticles;

		public SentrySprite(){
			texture( Assets.Sprites.NEW_SENTRY );

			idle = new Animation(1, true);
			idle.frames(texture.uvRect(0, 0, 8, 15));

			run = idle.clone();
			attack = idle.clone();
			charging = idle.clone();
			die = idle.clone();
			zap = idle.clone();

			play( idle );
		}

		@Override
		public void zap( int pos ) {
			idle();
			flash();
			emitter().burst(MagicMissile.WardParticle.UP, 2);
			if (Actor.findChar(pos) != null){
				parent.add(new Beam.DeathRay(center(), Actor.findChar(pos).sprite.center()));
			} else {
				parent.add(new Beam.DeathRay(center(), DungeonTilemap.raisedTileCenterToWorld(pos)));
			}
			((Sentry)ch).onZapComplete();
		}

		@Override
		public void link(Char ch) {
			super.link(ch);

			chargeParticles = centerEmitter();
			chargeParticles.autoKill = false;
			chargeParticles.pour(MagicMissile.MagicParticle.ATTRACTING, 0.05f);
			chargeParticles.on = false;

			if (((Sentry)ch).curChargeDelay != ((Sentry) ch).initialChargeDelay){
				play(charging);
			}
		}

		@Override
		public void die() {
			super.die();
			if (chargeParticles != null){
				chargeParticles.on = false;
			}
		}

		@Override
		public void kill() {
			super.kill();
			if (chargeParticles != null){
				chargeParticles.killAndErase();
			}
		}

		public void charge(){
			play(charging);
			if (visible) Sample.INSTANCE.play( Assets.Sounds.CHARGEUP );
		}

		@Override
		public void play(Animation anim) {
			if (chargeParticles != null) chargeParticles.on = anim == charging;
			super.play(anim);
		}

		private float baseY = Float.NaN;

		@Override
		public void place(int cell) {
			super.place(cell);
			baseY = y;
		}

		@Override
		public void turnTo(int from, int to) {
			//do nothing
		}

		@Override
		public void update() {
			super.update();
			if (chargeParticles != null){
				chargeParticles.pos( center() );
				chargeParticles.visible = visible;
			}

			if (!paused){
				if (Float.isNaN(baseY)) baseY = y;
				y = baseY + (float) Math.sin(Game.timeTotal);
				shadowOffset = 0.25f - 0.8f*(float) Math.sin(Game.timeTotal);
			}
		}

	}

	public static class Spawner extends NPC {
		{
			spriteClass = SpawnerSprite.class;

			properties.add(Property.IMMOVABLE);
		}

		@Override
		public void beckon(int cell) {
			//do nothing
		}

		@Override
		public int defenseSkill( Char enemy ) {
			return INFINITE_EVASION;
		}

		@Override
		public void damage( int dmg, Object src ) {
			//do nothing
		}

		@Override
		public boolean add( Buff buff ) {
			return false;
		}

		@Override
		public boolean reset() {
			return true;
		}

		@Override
		public boolean interact(Char c) {
			return true;
		}

		private static final String SPAWN_COOLDOWN = "spawnCooldown";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(SPAWN_COOLDOWN, spawnCooldown);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			spawnCooldown = bundle.getFloat(SPAWN_COOLDOWN);
		}

		private float spawnCooldown = 0;

		@Override
		protected boolean act() {
			if (fieldOfView == null || fieldOfView.length != Dungeon.level.length()) {
				fieldOfView = new boolean[Dungeon.level.length()];
			}
			Dungeon.level.updateFieldOfView(this, fieldOfView);

			if (properties().contains(Property.IMMOVABLE)) {
				throwItems();
			}

			if (spawnCooldown > 20) {
				spawnCooldown = 20;
			}

			if (Dungeon.hero != null) {
				if (fieldOfView[Dungeon.hero.pos]
						&& Dungeon.level.map[Dungeon.hero.pos] == Terrain.EMPTY_SP
						&& Dungeon.hero.buff(LostInventory.class) == null) {
					((SpawnerSprite) sprite).charge();
					CellEmitter.center(pos).start(Speck.factory(Speck.SCREAM), 0.3f, 3);
					spawnCooldown--;
				} else {
					sprite.idle();
				}
				spawnCooldown--;

				if (spawnCooldown <= 0) {

					//we don't want spawners to store multiple brutes
					if (spawnCooldown < -20) {
						spawnCooldown = -20;
					}

					ArrayList<Integer> candidates = new ArrayList<>();
					for (int n : PathFinder.NEIGHBOURS8) {
						if (Dungeon.level.passable[pos + n] && Actor.findChar(pos + n) == null) {
							candidates.add(pos + n);
						}
					}

					if (!candidates.isEmpty()) {
						Mob spawn;
						switch (Random.Int(3)) {
							case 0: default:
								spawn = new TempleGuard();
								break;
							case 1:
								spawn = new TempleBrute();
								break;
							case 2:
								spawn = Random.oneOf(new TempleRedShaman(), new TempleBlueShaman(), new TemplePurpleShaman());
								break;
						}

						spawn.pos = Random.element(candidates);
						spawn.state = spawn.HUNTING;

						GameScene.add(spawn, 1);
						Dungeon.level.occupyCell(spawn);

						if (sprite.visible) {
							Actor.add(new Pushing(spawn, pos, spawn.pos));
						}

						spawnCooldown += 20;
					}
				}
				alerted = false;
			}
			return super.act();
		}
	}

	public static class SpawnerSprite extends MobSprite {

		private Animation charging;
		private Emitter chargeParticles;

		public SpawnerSprite(){
			texture( Assets.Sprites.NEW_SENTRY );

			idle = new Animation(1, true);
			idle.frames(texture.uvRect(8, 0, 16, 15));

			run = idle.clone();
			attack = idle.clone();
			charging = idle.clone();
			die = idle.clone();
			zap = idle.clone();

			play( idle );
		}

		@Override
		public void link(Char ch) {
			super.link(ch);

			chargeParticles = centerEmitter();
			chargeParticles.autoKill = false;
			chargeParticles.pour(MagicMissile.MagicParticle.ATTRACTING, 0.05f);
			chargeParticles.on = false;

			if (((Spawner)ch).spawnCooldown != 20f){
				play(charging);
			}
		}

		@Override
		public void die() {
			super.die();
			if (chargeParticles != null){
				chargeParticles.on = false;
			}
		}

		@Override
		public void kill() {
			super.kill();
			if (chargeParticles != null){
				chargeParticles.killAndErase();
			}
		}

		public void charge(){
			play(charging);
		}

		@Override
		public void play(Animation anim) {
			if (chargeParticles != null) chargeParticles.on = anim == charging;
			super.play(anim);
		}

		private float baseY = Float.NaN;

		@Override
		public void place(int cell) {
			super.place(cell);
			baseY = y;
		}

		@Override
		public void turnTo(int from, int to) {
			//do nothing
		}

		@Override
		public void update() {
			super.update();
			if (chargeParticles != null){
				chargeParticles.pos( center() );
				chargeParticles.visible = visible;
			}

			if (!paused){
				if (Float.isNaN(baseY)) baseY = y;
				y = baseY + (float) Math.sin(Game.timeTotal);
				shadowOffset = 0.25f - 0.8f*(float) Math.sin(Game.timeTotal);
			}
		}
	}

	public static class TempleBrute extends Brute {
		{
			properties.add(Property.BOSS_MINION);
			state = HUNTING;
		}
	}

	public static class TempleRedShaman extends Shaman.RedShaman {
		{
			properties.add(Property.BOSS_MINION);
			state = HUNTING;
		}
	}

	public static class TempleBlueShaman extends Shaman.BlueShaman {
		{
			properties.add(Property.BOSS_MINION);
			state = HUNTING;
		}
	}

	public static class TemplePurpleShaman extends Shaman.PurpleShaman {
		{
			properties.add(Property.BOSS_MINION);
			state = HUNTING;
		}
	}

	public static class TempleGuard extends Guard {
		{
			properties.add(Property.BOSS_MINION);
			state = HUNTING;
		}
	}
}