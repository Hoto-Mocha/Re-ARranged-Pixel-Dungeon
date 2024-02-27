/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.sprites;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PurpleParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShaftParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.FT.FT;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.LG.LG;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class GhostSprite extends MobSprite {

	private int cellToAttack;
	
	public GhostSprite() {
		super();
		
		texture( Assets.Sprites.GHOST );
		
		TextureFilm frames = new TextureFilm( texture, 14, 15 );
		
		idle = new Animation( 5, true );
		idle.frames( frames, 0, 1 );
		
		run = new Animation( 10, true );
		run.frames( frames, 0, 1 );

		attack = new Animation( 10, false );
		attack.frames( frames, 0, 2, 3 );

		zap = new Animation( 10, false );
		zap.frames( frames, 0, 2, 3 );

		die = new Animation( 8, false );
		die.frames( frames, 0, 4, 5, 6, 7 );
		
		play( idle );
	}
	
	@Override
	public void draw() {
		Blending.setLightMode();
		super.draw();
		Blending.setNormalMode();
	}
	
	@Override
	public void die() {
		super.die();
		emitter().start( ShaftParticle.FACTORY, 0.3f, 4 );
		emitter().start( Speck.factory( Speck.LIGHT ), 0.2f, 3 );
	}
	
	@Override
	public int blood() {
		return 0xFFFFFF;
	}

	@Override
	public void attack( int cell ) {
		if (((DriedRose.GhostHero)ch).willingToShoot()) {

			cellToAttack = cell;
			zap(cell);

		} else {

			super.attack( cell );

		}
	}

	@Override
	public void onComplete( Animation anim ) {
		if (anim == zap) {
			idle();
			DriedRose rose = Dungeon.hero.belongings.getItem(DriedRose.class);
			if (rose != null && rose.ghostWeapon() instanceof Gun) {
				Gun.Bullet bullet = ((Gun)rose.ghostWeapon()).knockBullet();
				if (bullet instanceof LG.LGBullet) {
					if (cellToAttack != ch.pos) {
						Ballistica aim = new Ballistica(ch.pos, cellToAttack, Ballistica.WONT_STOP);
						ArrayList<Char> chars = new ArrayList<>();
						int maxDist = 2*(bullet.tier+1);
						int dist = Math.min(aim.dist, maxDist);
						int cells = aim.path.get(Math.min(aim.dist, dist));
						boolean terrainAffected = false;
						for (int c : aim.subPath(1, maxDist)) {

							Char enemy;
							if ((enemy = Actor.findChar( c )) != null && enemy != Actor.findChar(cellToAttack)) {
								chars.add( enemy );
							}

							if (Dungeon.level.flamable[c]) {
								Dungeon.level.destroy( c );
								GameScene.updateMap( c );
								terrainAffected = true;

							}

							CellEmitter.center( c ).burst( PurpleParticle.BURST, Random.IntRange( 1, 2 ) );
						}
						if (terrainAffected) {
							Dungeon.observe();
						}

						ch.sprite.parent.add(new Beam.DeathRay(ch.sprite.center(), DungeonTilemap.raisedTileCenterToWorld( cells )));

						for (Char enemy : chars) {
							if (ch.attack(enemy)) {
								enemy.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10+bullet.buffedLvl() );
							}
							if (enemy == hero && !enemy.isAlive()) {
								Dungeon.fail(getClass());
								Badges.validateDeathFromFriendlyMagic();
								GLog.n(Messages.get(this, "ondeath"));
							}
						}
						ch.onAttackComplete();
					}
				} else if (bullet instanceof FT.FTBullet) {
					if (cellToAttack != ch.pos) {
						Ballistica aim = new Ballistica(ch.pos, cellToAttack, Ballistica.WONT_STOP);
						int maxDist = bullet.tier + 1;
						int dist = Math.min(aim.dist, maxDist);
						ConeAOE cone = new ConeAOE(aim,
								dist,
								30,
								Ballistica.STOP_TARGET | Ballistica.STOP_SOLID | Ballistica.IGNORE_SOFT_SOLID);
						//cast to cells at the tip, rather than all cells, better performance.
						for (Ballistica ray : cone.outerRays){
							((MagicMissile)ch.sprite.parent.recycle( MagicMissile.class )).reset(
									MagicMissile.FIRE_CONE,
									ch.sprite,
									ray.path.get(ray.dist),
									null
							);
						}
						ArrayList<Char> chars = new ArrayList<>();
						for (int cells : cone.cells){
							//knock doors open
							if (Dungeon.level.map[cells] == Terrain.DOOR){
								Level.set(cells, Terrain.OPEN_DOOR);
								GameScene.updateMap(cells);
							}

							//only ignite cells directly near caster if they are flammable
							if (!(Dungeon.level.adjacent(ch.pos, cells) && !Dungeon.level.flamable[cells])) {
								GameScene.add(Blob.seed(cells, 2, Fire.class));
							}

							Char enemy = Actor.findChar(cells);
							if (enemy != null && enemy.alignment != hero.alignment && enemy != Actor.findChar(cellToAttack)){
								chars.add(enemy);
							}
						}
						for (Char enemy : chars) {
							ch.attack(enemy);
							if (enemy == hero && !enemy.isAlive()) {
								Dungeon.fail(getClass());
								Badges.validateDeathFromFriendlyMagic();
								GLog.n(Messages.get(this, "ondeath"));
							}
						}

						//final zap at 2/3 distance, for timing of the actual effect
						MagicMissile.boltFromChar(ch.sprite.parent,
								MagicMissile.FIRE_CONE,
								ch.sprite,
								cone.coreRay.path.get(dist * 2 / 3),
								new Callback() {
									@Override
									public void call() {
										ch.onAttackComplete();
									}
								});
					}
				} else {
					bullet.image = ItemSpriteSheet.GHOST_BULLET;
					((MissileSprite)parent.recycle( MissileSprite.class )).
							reset( this, cellToAttack, bullet, new Callback() {
								@Override
								public void call() {
									ch.onAttackComplete();
								}
							} );
				}
				bullet.throwSound();
				CellEmitter.get(ch.pos).burst(SmokeParticle.FACTORY, 2);
				CellEmitter.center(ch.pos).burst(BlastParticle.FACTORY, 2);
			}

		} else {
			super.onComplete( anim );
		}
	}
}
