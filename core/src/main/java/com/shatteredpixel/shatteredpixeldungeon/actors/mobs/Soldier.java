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

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SoldierSprite;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

public class Soldier extends Mob {
	
	{
		spriteClass = SoldierSprite.class;
		
		HP = HT = 150;
		defenseSkill = 20;
		viewDistance = Light.DISTANCE;
		
		EXP = 15;
		maxLvl = 30;
		
		loot = Generator.Category.WEAPON;
		lootChance = 0.1667f; //by default, see rollToDropLoot()
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 40, 50 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 45;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 20);
	}
	
	@Override
	protected boolean canAttack( Char enemy ) {
		Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
		return !Dungeon.level.adjacent( pos, enemy.pos ) && attack.collisionPos == enemy.pos;
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		
		return damage;
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
	public float speed() {
		return super.speed()*(5/6f); //5/6 movespeed
	}
	
	@Override
	public void aggro(Char ch) {
		//cannot be aggroed to something it can't see
		if (ch == null || fieldOfView == null || fieldOfView[ch.pos]) {
			super.aggro(ch);
		}
	}

	@Override
	public void rollToDropLoot() {
		//each drop makes future drops 1/2 as likely
		// so loot chance looks like: 1/6, 1/12, 1/24, 1/48, etc.
		lootChance *= Math.pow(1/2f, Dungeon.LimitedDrops.SOLDIER_WEP.count);
		super.rollToDropLoot();
	}

	@Override
	public Item createLoot() {
		if (depth == 30) {
			return null;
		} else {
			Dungeon.LimitedDrops.SOLDIER_WEP.count++;
			return super.createLoot();
		}
	}

	@Override
	public void die( Object cause ) {
		super.die(cause);
		Statistics.soldierKilled = true;
		Badges.validateGunnerUnlock();
	}
	
}
