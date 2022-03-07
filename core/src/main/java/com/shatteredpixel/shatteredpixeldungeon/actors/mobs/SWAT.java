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

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Handgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Magnum;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RocketLauncher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifle;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SWATSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SoldierSprite;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

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

	public SWAT.SWATWeapon swatWeapon = Random.oneOf(SWATWeapon.HANDGUN, SWATWeapon.SNIPER, SWATWeapon.MACHINEGUN);
	
	@Override
	public int damageRoll() {
		int dmg;
		if (swatWeapon == SWATWeapon.SNIPER) {
			dmg = Random.NormalIntRange( 60, 90 );
		} else if (swatWeapon == SWATWeapon.HANDGUN) {
			dmg = Random.NormalIntRange( 40, 50 );
		} else {
			dmg = Random.NormalIntRange( 10, 30 );
		}
		return dmg;
	}

	@Override
	public float speed() {
		if (swatWeapon == SWATWeapon.SNIPER) {
			return super.speed();
		} else if (swatWeapon == SWATWeapon.HANDGUN) {
			return super.speed()*2f;
		} else {
			return super.speed()*0.5f;
		}

	}
	
	@Override
	public int attackSkill( Char target ) {
		int accuracy;
		if (swatWeapon == SWATWeapon.SNIPER) {
			accuracy = 60;
		} else if (swatWeapon == SWATWeapon.HANDGUN) {
			accuracy = 45;
		} else {
			accuracy = 20;
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
			return super.attackDelay();
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
	protected Item createLoot(){
		if (swatWeapon == SWATWeapon.SNIPER) {
			return new AntimaterRifle().upgrade(1+Random.Int(3)).identify();
		} else if (swatWeapon == SWATWeapon.HANDGUN) {
			return new LargeHandgun().upgrade(1+Random.Int(3)).identify();
		} else {
			return new MiniGun().upgrade(1+Random.Int(3)).identify();
		}
	}

	@Override
	public String description() {
		if (swatWeapon == SWATWeapon.SNIPER) {
			return Messages.get(this, "sniper_desc");
		} else if (swatWeapon == SWATWeapon.HANDGUN) {
			return Messages.get(this, "handgun_desc");
		} else {
			return Messages.get(this, "machinegun_desc");
		}

	}
	
}
