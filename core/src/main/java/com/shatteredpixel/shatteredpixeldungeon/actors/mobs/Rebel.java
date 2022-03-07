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
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KingsCrown;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.LloydsBeacon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssultRifle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Handgun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HeavyMachinegun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Magnum;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RocketLauncher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShotGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SniperRifle;
import com.shatteredpixel.shatteredpixeldungeon.levels.CityBossLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.LabsBossLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.PrisonBossLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RebelSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SoldierSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.HashSet;

public class Rebel extends Mob {
	
	{
		spriteClass = RebelSprite.class;
		
		HP = HT = (Dungeon.isChallenged(Challenges.STRONGER_BOSSES)) ? 1800 : 1500;
		defenseSkill = 30;
		viewDistance = 10;
		
		EXP = 100;
		maxLvl = 30;
	}

	int summonCooldown = (Dungeon.isChallenged(Challenges.STRONGER_BOSSES) ? 20 : 30);
	int damageTaken = 0;

	public boolean isDied = false;
	private static boolean telling_1 = false;
	private static boolean telling_2 = false;
	private static boolean telling_3 = false;
	private static boolean telling_4 = false;
	private static boolean telling_5 = false;

	public boolean isDied() {
		return isDied;
	}
	
	@Override
	public int damageRoll() {
		int dmg;
		if (Dungeon.isChallenged(Challenges.STRONGER_BOSSES)) {
			dmg = Random.NormalIntRange( 40, 60 );
		} else {
			dmg = Random.NormalIntRange( 30, 50 );
		}
		return dmg;
	}
	
	@Override
	public int attackSkill( Char target ) {
		return (Dungeon.isChallenged(Challenges.STRONGER_BOSSES) && Random.Int(10) == 0) ? 70 : 35;
	}
	
	@Override
	public int drRoll() {
		int dr;
		if (Dungeon.isChallenged(Challenges.STRONGER_BOSSES)) {
			dr = Random.NormalIntRange(10, 30);
		} else {
			dr = Random.NormalIntRange(5, 30);
		}
		return dr;
	}
	
	@Override
	protected boolean canAttack( Char enemy ) {
		return true;
	}

	@Override
	public void notice() {
		super.notice();
		if (!BossHealthBar.isAssigned()) {
			BossHealthBar.assignBoss(this);
			yell(Messages.get(this, "notice"));
			for (Char ch : Actor.chars()){
				if (ch instanceof DriedRose.GhostHero){
					((DriedRose.GhostHero) ch).sayBoss();
				}
			}
		}
	}

	@Override
	protected boolean act() {

		summonCooldown--;

		if (this.HP < 5*this.HT/6 && !telling_1) {
			yell(Messages.get(this, "telling_1"));
			telling_1 = true;
		}
		if (this.HP < 4*this.HT/6 && !telling_2) {
			yell(Messages.get(this, "telling_2"));
			telling_2 = true;
		}
		if (this.HP < 3*this.HT/6 && !telling_3) {
			yell(Messages.get(this, "telling_3"));
			telling_3 = true;
		}
		if (this.HP < 2*this.HT/6 && !telling_4) {
			yell(Messages.get(this, "telling_4"));
			telling_4 = true;
		}
		if (this.HP < this.HT/6 && !telling_5) {
			yell(Messages.get(this, "telling_5"));
			telling_5 = true;
		}

		if (summonCooldown <= 0 && Dungeon.level instanceof LabsBossLevel) {
			Soldier soldier1 = new Soldier();
			soldier1.state = soldier1.HUNTING;
			soldier1.pos = 3+16*33;
			GameScene.add( soldier1 );
			soldier1.beckon(Dungeon.hero.pos);

			Soldier soldier2 = new Soldier();
			soldier2.state = soldier1.HUNTING;
			soldier2.pos = 29+16*33;
			GameScene.add( soldier2 );
			soldier2.beckon(Dungeon.hero.pos);

			Medic medic = new Medic();
			medic.state = soldier1.HUNTING;
			medic.pos = 16+3*33;
			GameScene.add( medic );
			medic.beckon(Dungeon.hero.pos);
			summonCooldown = (Dungeon.isChallenged(Challenges.STRONGER_BOSSES) ? 20 : 30);

			sprite.centerEmitter().start(Speck.factory(Speck.SCREAM), 0.4f, 2);
			Sample.INSTANCE.play(Assets.Sounds.CHALLENGE);
		}
		return super.act();
	}

	private HashSet<Mob> getSubjects(){
		HashSet<Mob> subjects = new HashSet<>();
		for (Mob m : Dungeon.level.mobs){
			if (m.alignment == alignment && (m instanceof Soldier || m instanceof Medic || m instanceof Supression || m instanceof Researcher || m instanceof Tank)){
				subjects.add(m);
			}
		}
		return subjects;
	}

	@Override
	public void die(Object cause) {

		GameScene.bossSlain();

		super.die( cause );

		//Badges.validateBossSlain(); //TODO 뱃지 추가 필요

		Dungeon.level.unseal();

		for (Mob m : getSubjects()){
			m.die(null);
		}

		LloydsBeacon beacon = Dungeon.hero.belongings.getItem(LloydsBeacon.class);
		if (beacon != null) {
			beacon.upgrade();
		}

		yell( Messages.get(this, "defeated") );

		isDied = true;
	}

	@Override
	public int defenseProc( Char enemy, int damage ) {
		damage = super.defenseProc( enemy, damage );
		if (damage >= 50) {
			damage = 50;
		}
		damageTaken += damage;
		if (damageTaken >= 250) {
			Buff.affect(this, Barrier.class).setShield(200);
			damageTaken = 0;
		}
		int newPos;
		LabsBossLevel level = (LabsBossLevel) Dungeon.level;
		if (Dungeon.level instanceof LabsBossLevel) {
			if (Dungeon.isChallenged(Challenges.STRONGER_BOSSES)) {
				if (Random.Int(2) > 0) {
					do {
						newPos = level.randomCellPos();
					} while (level.map[newPos] == Terrain.BARRICADE || Actor.findChar(newPos) != null);

					if (level.heroFOV[pos]) CellEmitter.get( pos ).burst( Speck.factory( Speck.WOOL ), 6 );

					sprite.move( pos, newPos );
					move( newPos );

					if (level.heroFOV[newPos]) CellEmitter.get( newPos ).burst( Speck.factory( Speck.WOOL ), 6 );
					Sample.INSTANCE.play( Assets.Sounds.PUFF );
					GameScene.flash( 0x80FFFFFF );
					Sample.INSTANCE.play( Assets.Sounds.BLAST );
					Buff.affect(Dungeon.hero, Blindness.class, 5f);
				}
			} else {
				if (Random.Int(4) > 0) {
					do {
						newPos = level.randomCellPos();
					} while (level.map[newPos] == Terrain.BARRICADE || Actor.findChar(newPos) != null);

					if (level.heroFOV[pos]) CellEmitter.get( pos ).burst( Speck.factory( Speck.WOOL ), 6 );

					sprite.move( pos, newPos );
					move( newPos );

					if (level.heroFOV[newPos]) CellEmitter.get( newPos ).burst( Speck.factory( Speck.WOOL ), 6 );
					Sample.INSTANCE.play( Assets.Sounds.PUFF );
				}
			}
		}
		return damage;
	}
	
}
