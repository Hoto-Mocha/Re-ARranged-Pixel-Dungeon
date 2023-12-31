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

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.depth;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.HG_T6;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.alchemy.SR_T6;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.MG.MG_T5;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SWATSprite;
import com.watabou.utils.Random;

public class SWAT extends Mob {
	
	{
		spriteClass = SWATSprite.class;
		
		HP = HT = 150;
		defenseSkill = 20;
		viewDistance = Light.DISTANCE;
		
		EXP = 15;
		maxLvl = 30;

		lootChance = 1;
	}

	public enum SWATWeapon {
		HANDGUN, SNIPER, MACHINEGUN
	}

	public SWATWeapon swatWeapon = Random.oneOf(SWATWeapon.HANDGUN, SWATWeapon.SNIPER, SWATWeapon.MACHINEGUN);
	
	@Override
	public int damageRoll() {
		int dmg;
		switch (swatWeapon) {
			case HANDGUN: default:
				dmg = Random.NormalIntRange( 40, 50 );
				break;
			case SNIPER:
				dmg = Random.NormalIntRange( 60, 90 );
				break;
			case MACHINEGUN:
				dmg = Random.NormalIntRange( 10, 30 );
				break;
		}
		return dmg;
	}

	@Override
	public float speed() {
		switch (swatWeapon) {
			case HANDGUN: default:
				return super.speed()*2f;
			case SNIPER:
				return super.speed();
			case MACHINEGUN:
				return super.speed()*0.5f;
		}
	}
	
	@Override
	public int attackSkill( Char target ) {
		int accuracy;
		switch (swatWeapon) {
			case HANDGUN: default:
				accuracy = 45;
				break;
			case SNIPER:
				accuracy = 60;
				break;
			case MACHINEGUN:
				accuracy = 20;
				break;
		}
		return accuracy;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 20);
	}

	@Override
	public float attackDelay() {
		if (swatWeapon == SWATWeapon.SNIPER) {
			return super.attackDelay()*3f;
		} else if (swatWeapon == SWATWeapon.HANDGUN) {
			return super.attackDelay()*0.5f;
		} else {
			return super.attackDelay()*0.333f;
		}
	}
	
	@Override
	protected boolean canAttack( Char enemy ) {
		Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
		return !Dungeon.level.adjacent( pos, enemy.pos ) && attack.collisionPos == enemy.pos;
	}
	
	@Override
	protected boolean getCloser( int target ) {
		if (state == HUNTING) {
			return enemySeen && getFurther( target );
		} else {
			return super.getCloser( target );
		}
	}
	
	@Override
	public void aggro(Char ch) {
		//cannot be aggroed to something it can't see
		if (ch == null || fieldOfView == null || fieldOfView[ch.pos]) {
			super.aggro(ch);
		}
	}

	@Override
	public Item createLoot(){
		if (depth == 30) {
			return null;
		} else {
			switch (swatWeapon) {
				case HANDGUN: default:
					return new HG_T6().upgrade(Random.Int(1, 4));
				case SNIPER:
					return new SR_T6().upgrade(Random.Int(1, 4));
				case MACHINEGUN:
					return new MG_T5().upgrade(Random.Int(1, 4));
			}
		}
	}

	@Override
	public String description() {
		switch (swatWeapon) {
			case HANDGUN: default:
				return Messages.get(this, "handgun_desc");
			case SNIPER:
				return Messages.get(this, "sniper_desc");
			case MACHINEGUN:
				return Messages.get(this, "machinegun_desc");
		}
	}
	
}
