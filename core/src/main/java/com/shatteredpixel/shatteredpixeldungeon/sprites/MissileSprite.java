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

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AntimaterRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Crossbow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrudePistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DualPistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldenPistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Handgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HuntingRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeHandgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeHandgunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LargeHandgunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Magnum;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagnumHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MiniGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Pistol;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PistolHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RPG7;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RocketLauncher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifleHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunAP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SubMachinegunHP;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Bolas;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.FishingSpear;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.HeavyBoomerang;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Javelin;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Kunai;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Shuriken;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingSpear;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Trident;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.Dart;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.Visual;
import com.watabou.noosa.tweeners.PosTweener;
import com.watabou.noosa.tweeners.Tweener;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;

import java.util.HashMap;

public class MissileSprite extends ItemSprite implements Tweener.Listener {

	private static final float SPEED	= 240f;
	
	private Callback callback;
	
	public void reset( int from, int to, Item item, Callback listener ) {
		reset(Dungeon.level.solid[from] ? DungeonTilemap.raisedTileCenterToWorld(from) : DungeonTilemap.raisedTileCenterToWorld(from),
				Dungeon.level.solid[to] ? DungeonTilemap.raisedTileCenterToWorld(to) : DungeonTilemap.raisedTileCenterToWorld(to),
				item, listener);
	}

	public void reset( Visual from, int to, Item item, Callback listener ) {
		reset(from.center(),
				Dungeon.level.solid[to] ? DungeonTilemap.raisedTileCenterToWorld(to) : DungeonTilemap.raisedTileCenterToWorld(to),
				item, listener );
	}

	public void reset( int from, Visual to, Item item, Callback listener ) {
		reset(Dungeon.level.solid[from] ? DungeonTilemap.raisedTileCenterToWorld(from) : DungeonTilemap.raisedTileCenterToWorld(from),
				to.center(),
				item, listener );
	}

	public void reset( Visual from, Visual to, Item item, Callback listener ) {
		reset(from.center(), to.center(), item, listener );
	}

	public void reset( PointF from, PointF to, Item item, Callback listener) {
		revive();

		if (item == null)   view(0, null);
		else                view( item );

		setup( from,
				to,
				item,
				listener );
	}
	
	private static final int DEFAULT_ANGULAR_SPEED = 720;
	
	private static final HashMap<Class<?extends Item>, Integer> ANGULAR_SPEEDS = new HashMap<>();
	static {
		ANGULAR_SPEEDS.put(Dart.class,          0);
		ANGULAR_SPEEDS.put(ThrowingKnife.class, 0);
		ANGULAR_SPEEDS.put(FishingSpear.class,  0);
		ANGULAR_SPEEDS.put(ThrowingSpear.class, 0);
		ANGULAR_SPEEDS.put(Kunai.class,         0);
		ANGULAR_SPEEDS.put(Javelin.class,       0);
		ANGULAR_SPEEDS.put(Trident.class,       0);
		
		ANGULAR_SPEEDS.put(SpiritBow.SpiritArrow.class,       0);
		ANGULAR_SPEEDS.put(ScorpioSprite.ScorpioShot.class,   0);

		ANGULAR_SPEEDS.put(CrudePistol.Bullet.class,          0);
		ANGULAR_SPEEDS.put(CrudePistolAP.Bullet.class,        0);
		ANGULAR_SPEEDS.put(CrudePistolHP.Bullet.class,        0);
		ANGULAR_SPEEDS.put(Pistol.Bullet.class,               0);
		ANGULAR_SPEEDS.put(PistolAP.Bullet.class,             0);
		ANGULAR_SPEEDS.put(PistolHP.Bullet.class,             0);
		ANGULAR_SPEEDS.put(GoldenPistol.Bullet.class,         0);
		ANGULAR_SPEEDS.put(GoldenPistolAP.Bullet.class,       0);
		ANGULAR_SPEEDS.put(GoldenPistolHP.Bullet.class,       0);
		ANGULAR_SPEEDS.put(Handgun.Bullet.class,              0);
		ANGULAR_SPEEDS.put(HandgunAP.Bullet.class,            0);
		ANGULAR_SPEEDS.put(HandgunHP.Bullet.class,            0);
		ANGULAR_SPEEDS.put(Magnum.Bullet.class,               0);
		ANGULAR_SPEEDS.put(MagnumAP.Bullet.class,             0);
		ANGULAR_SPEEDS.put(MagnumHP.Bullet.class,             0);
		ANGULAR_SPEEDS.put(HuntingRifle.Bullet.class,         0);
		ANGULAR_SPEEDS.put(HuntingRifleAP.Bullet.class,       0);
		ANGULAR_SPEEDS.put(HuntingRifleHP.Bullet.class,       0);
		ANGULAR_SPEEDS.put(SniperRifle.Bullet.class,          0);
		ANGULAR_SPEEDS.put(SniperRifleAP.Bullet.class,        0);
		ANGULAR_SPEEDS.put(SniperRifleHP.Bullet.class,        0);
		ANGULAR_SPEEDS.put(DualPistol.Bullet.class,           0);
		ANGULAR_SPEEDS.put(DualPistolAP.Bullet.class,         0);
		ANGULAR_SPEEDS.put(DualPistolHP.Bullet.class,         0);
		ANGULAR_SPEEDS.put(SubMachinegun.Bullet.class,        0);
		ANGULAR_SPEEDS.put(SubMachinegunAP.Bullet.class,      0);
		ANGULAR_SPEEDS.put(SubMachinegunHP.Bullet.class,      0);
		ANGULAR_SPEEDS.put(AssultRifle.Bullet.class,          0);
		ANGULAR_SPEEDS.put(AssultRifleAP.Bullet.class,        0);
		ANGULAR_SPEEDS.put(AssultRifleHP.Bullet.class,        0);
		ANGULAR_SPEEDS.put(HeavyMachinegun.Bullet.class,      0);
		ANGULAR_SPEEDS.put(HeavyMachinegunAP.Bullet.class,    0);
		ANGULAR_SPEEDS.put(HeavyMachinegunHP.Bullet.class,    0);
		ANGULAR_SPEEDS.put(ShotGun.Bullet.class,              0);
		ANGULAR_SPEEDS.put(ShotGunAP.Bullet.class,            0);
		ANGULAR_SPEEDS.put(ShotGunHP.Bullet.class,            0);
	    ANGULAR_SPEEDS.put(RocketLauncher.Rocket.class,       0);

		ANGULAR_SPEEDS.put(MiniGun.Bullet.class,              0);
		ANGULAR_SPEEDS.put(MiniGunAP.Bullet.class,            0);
		ANGULAR_SPEEDS.put(MiniGunHP.Bullet.class,            0);
		ANGULAR_SPEEDS.put(LargeHandgun.Bullet.class,         0);
		ANGULAR_SPEEDS.put(LargeHandgunAP.Bullet.class,       0);
		ANGULAR_SPEEDS.put(LargeHandgunHP.Bullet.class,       0);
		ANGULAR_SPEEDS.put(AntimaterRifle.Bullet.class,       0);
		ANGULAR_SPEEDS.put(AntimaterRifleAP.Bullet.class,     0);
		ANGULAR_SPEEDS.put(AntimaterRifleHP.Bullet.class,     0);
		ANGULAR_SPEEDS.put(RPG7.Rocket.class,                 0);
		//720 is default
		
		ANGULAR_SPEEDS.put(HeavyBoomerang.class,1440);
		ANGULAR_SPEEDS.put(Bolas.class,         1440);
		
		ANGULAR_SPEEDS.put(Shuriken.class,      2160);
		
		ANGULAR_SPEEDS.put(TenguSprite.TenguShuriken.class,      2160);
	}

	//TODO it might be nice to have a source and destination angle, to improve thrown weapon visuals
	private void setup( PointF from, PointF to, Item item, Callback listener ){

		originToCenter();

		//adjust points so they work with the center of the missile sprite, not the corner
		from.x -= width()/2;
		to.x -= width()/2;
		from.y -= height()/2;
		to.y -= height()/2;

		this.callback = listener;

		point( from );

		PointF d = PointF.diff( to, from );
		speed.set(d).normalize().scale(SPEED);
		
		angularSpeed = DEFAULT_ANGULAR_SPEED;
		for (Class<?extends Item> cls : ANGULAR_SPEEDS.keySet()){
			if (cls.isAssignableFrom(item.getClass())){
				angularSpeed = ANGULAR_SPEEDS.get(cls);
				break;
			}
		}
		
		angle = 135 - (float)(Math.atan2( d.x, d.y ) / 3.1415926 * 180);
		
		if (d.x >= 0){
			flipHorizontal = false;
			updateFrame();
			
		} else {
			angularSpeed = -angularSpeed;
			angle += 90;
			flipHorizontal = true;
			updateFrame();
		}
		
		float speed = SPEED;
		if (item instanceof Dart && Dungeon.hero.belongings.weapon() instanceof Crossbow){
			speed *= 3f;
			
		} else if (item instanceof SpiritBow.SpiritArrow
				|| item instanceof ScorpioSprite.ScorpioShot
				|| item instanceof TenguSprite.TenguShuriken){
			speed *= 1.5f;
		} else if (item instanceof CrudePistol.Bullet
				||item instanceof CrudePistolAP.Bullet
				||item instanceof CrudePistolHP.Bullet
				||item instanceof Pistol.Bullet
				||item instanceof PistolAP.Bullet
				||item instanceof PistolHP.Bullet
				||item instanceof GoldenPistol.Bullet
				||item instanceof GoldenPistolAP.Bullet
				||item instanceof GoldenPistolHP.Bullet
				||item instanceof Handgun.Bullet
				||item instanceof HandgunAP.Bullet
				||item instanceof HandgunHP.Bullet
				||item instanceof Magnum.Bullet
				||item instanceof MagnumAP.Bullet
				||item instanceof MagnumHP.Bullet
				||item instanceof DualPistol.Bullet
				||item instanceof DualPistolAP.Bullet
				||item instanceof DualPistolHP.Bullet
				||item instanceof SubMachinegun.Bullet
				||item instanceof SubMachinegunAP.Bullet
				||item instanceof SubMachinegunHP.Bullet
				||item instanceof AssultRifle.Bullet
				||item instanceof AssultRifleAP.Bullet
				||item instanceof AssultRifleHP.Bullet
				||item instanceof HeavyMachinegun.Bullet
				||item instanceof HeavyMachinegunAP.Bullet
				||item instanceof HeavyMachinegunHP.Bullet
				||item instanceof HuntingRifle.Bullet
				||item instanceof HuntingRifleAP.Bullet
				||item instanceof HuntingRifleHP.Bullet
				||item instanceof SniperRifle.Bullet
				||item instanceof SniperRifleAP.Bullet
				||item instanceof SniperRifleHP.Bullet
				||item instanceof ShotGun.Bullet
				||item instanceof ShotGunAP.Bullet
				||item instanceof ShotGunHP.Bullet
				||item instanceof MiniGun.Bullet
				||item instanceof MiniGunAP.Bullet
				||item instanceof MiniGunHP.Bullet
				||item instanceof LargeHandgun.Bullet
				||item instanceof LargeHandgunAP.Bullet
				||item instanceof LargeHandgunHP.Bullet
				||item instanceof AntimaterRifle.Bullet
				||item instanceof AntimaterRifleAP.Bullet
				||item instanceof AntimaterRifleHP.Bullet
				||item instanceof RPG7.Rocket
		) {
			speed *= 3f;
		} else if (item instanceof RocketLauncher.Rocket) {
			speed *= 1.2f;
		}
		
		PosTweener tweener = new PosTweener( this, to, d.length() / speed );
		tweener.listener = this;
		parent.add( tweener );
	}

	@Override
	public void onComplete( Tweener tweener ) {
		kill();
		if (callback != null) {
			callback.call();
		}
	}
}
