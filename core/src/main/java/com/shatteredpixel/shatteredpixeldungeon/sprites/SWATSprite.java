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
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class SWATSprite extends MobSprite {

	private int cellToAttack;

	public SWATSprite() {
		super();
		
		texture( Assets.Sprites.SOLDIER );
		
		TextureFilm frames = new TextureFilm( texture, 12, 15 );

		idle = new Animation( 1, true );
		idle.frames( frames, 21, 21, 21, 22, 21, 21, 21, 22 );

		run = new Animation( 20, true );
		run.frames( frames, 23, 24, 25, 26, 27, 28 );

		attack = new Animation( 15, false );
		attack.frames( frames, 29, 30, 31, 21 );
		
		zap = attack.clone();

		die = new Animation( 20, false );
		die.frames( frames, 32, 33, 34, 35, 36, 35);
		
		play( idle );
	}
	
	@Override
	public void attack( int cell ) {
		if (!Dungeon.level.adjacent( cell, ch.pos )) {

			cellToAttack = cell;
			turnTo( ch.pos , cell );
			play( zap );

		} else {
			
			super.attack( cell );
			
		}
	}
	
	@Override
	public void onComplete( Animation anim ) {
		if (anim == zap) {
			idle();
			CellEmitter.get(ch.pos).burst(SmokeParticle.FACTORY, 2);
			CellEmitter.center(ch.pos).burst(BlastParticle.FACTORY, 2);
			Sample.INSTANCE.play( Assets.Sounds.HIT_CRUSH, 1, Random.Float(0.33f, 0.66f) );
			((MissileSprite)parent.recycle( MissileSprite.class )).
			reset( this, cellToAttack, new SWATShot(), new Callback() {

				@Override
				public void call() {
					ch.onAttackComplete();
				}
			} );
		} else {
			super.onComplete( anim );
		}
	}
	
	public class SWATShot extends Item {
		{
			image = ItemSpriteSheet.SNIPER_BULLET;
		}
	}
}
