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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.StableAPBullet;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.StableHPBullet;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.TankSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Tank extends Mob {

	{
		spriteClass = TankSprite.class;

		HP = HT = 300;
		defenseSkill = 10;

		EXP = 20;
		maxLvl = 30;
		
		lootChance = 0.1f; //10% drop rate

		properties.add(Property.INORGANIC);
		properties.add(Property.LARGE);

		HUNTING = new Hunting();
	}
	private int cannonCooldown = 0;

	private static final String CANNON_COOLDOWN = "cannon_cooldown";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CANNON_COOLDOWN, cannonCooldown);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		cannonCooldown = bundle.getInt(CANNON_COOLDOWN);
	}

	@Override
	protected boolean act() {
		cannonCooldown--;
		return super.act();
	}

	@Override
	public void onAttackComplete() {
		Sample.INSTANCE.play( Assets.Sounds.HIT_CRUSH, 1.5f, Random.Float(0.33f, 0.66f) );
		super.onAttackComplete();
	}

	@Override
	public int damageRoll() {
		int dmg = Random.NormalIntRange( 20, 40 );
		if (enemy != null && !Dungeon.level.adjacent( pos, enemy.pos )) dmg *= 2;
		return dmg;
	}

	@Override
	public int attackSkill( Char target ) {
		return 40;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(10, 50);
	}

	public static class Cannon{}

	private void zap() {
		spend( TICK );
		Sample.INSTANCE.play( Assets.Sounds.HIT_CRUSH, 1, Random.Float(0.33f, 0.66f));

		if (hit( this, enemy, true )) {
			if (enemy == Dungeon.hero && !enemy.isAlive()) {
				Dungeon.fail( getClass() );
				GLog.n( Messages.get(this, "cannon_kill") );
			}
		} else {
			enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
		}
	}

	@Override
	public Item createLoot(){
		return Random.oneOf(new StableAPBullet(), new StableHPBullet());
	}

	private class Hunting extends Mob.Hunting{

		@Override
		public boolean act(boolean enemyInFOV, boolean justAlerted) {
			if (!enemyInFOV || canAttack(enemy)) {
				return super.act(enemyInFOV, justAlerted);
			} else {
				enemySeen = true;
				target = enemy.pos;

				int oldPos = pos;

				if (cannonCooldown <= 0 && distance(enemy) >= 2 && Random.Int(100/distance(enemy)) == 0){
					if (sprite != null && (sprite.visible || enemy.sprite.visible)) {
						sprite.zap( enemy.pos );
						return false;
					} else {
						zap();
						return true;
					}

				} else if (getCloser( target )) {
					spend( 1 / speed() );
					return moveSprite( oldPos,  pos );
				} else {
					spend( TICK );
					return true;
				}

			}
		}
	}

}
