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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShaftParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

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
		if (!Dungeon.level.adjacent( cell, ch.pos )) {

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
				bullet.image = ItemSpriteSheet.GHOST_BULLET;
				((MissileSprite)parent.recycle( MissileSprite.class )).
						reset( this, cellToAttack, bullet, new Callback() {
							@Override
							public void call() {
								ch.onAttackComplete();
							}
						} );
				bullet.throwSound();
				CellEmitter.get(ch.pos).burst(SmokeParticle.FACTORY, 2);
				CellEmitter.center(ch.pos).burst(BlastParticle.FACTORY, 2);
			}

		} else {
			super.onComplete( anim );
		}
	}
}
