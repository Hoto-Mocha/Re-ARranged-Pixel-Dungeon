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

package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

public class TankSprite extends MobSprite {

	int cellToAttack;

	public TankSprite() {
		super();

		texture( Assets.Sprites.TANK );

		TextureFilm frames = new TextureFilm( texture, 25, 15 );

		idle = new Animation( 10, true );
		idle.frames( frames, 0, 1 );

		run = new Animation( 10, true );
		run.frames( frames, 2, 3 );

		attack = new Animation( 30, false );
		attack.frames( frames, 4, 5, 6, 5, 6 );

		zap = new Animation( 15, false );
		zap.frames( frames, 7, 8, 8, 9, 9, 8, 8, 0, 0 );

		die = new Animation( 40, false );
		die.frames( frames, 10, 10, 11, 11, 11, 12, 12, 12, 12, 13, 13, 13, 13, 14, 14, 14, 15, 15, 16, 16 );

		play( idle );
	}

	public void zap( int cell ) {

		cellToAttack = cell;

		turnTo( ch.pos , cell );
		play( zap );

		CellEmitter.get(ch.pos).burst(SmokeParticle.FACTORY, 2);
		CellEmitter.center(ch.pos).burst(BlastParticle.FACTORY, 4);
		((MissileSprite)parent.recycle( MissileSprite.class )).
				reset( this, cellToAttack, new TankShot(), new Callback() {

					@Override
					public void call() {
						ch.onAttackComplete();
					}
				} );
	}

	@Override
	public void die() {
		emitter().burst( Speck.factory( Speck.WOOL ), 8 );
		super.die();
	}

	@Override
	public void onComplete( Animation anim ) {
		if (anim == zap) {
			idle();
		}
		super.onComplete( anim );
	}

	@Override
	public int blood() {
		return 0xFFFFFF88;
	}

	public class TankShot extends Item {
		{
			image = ItemSpriteSheet.ROCKET;
		}
	}
}