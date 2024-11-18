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
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Dread;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.LloydsBeacon;
import com.shatteredpixel.shatteredpixeldungeon.levels.LabsBossLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RebelSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Rebel extends Mob {
	
	{
		spriteClass = RebelSprite.class;
		
		HP = HT = (Dungeon.isChallenged(Challenges.STRONGER_BOSSES)) ? 1500 : 1200;
		defenseSkill = 30;
		viewDistance = 10;
		
		EXP = 100;
		maxLvl = 30;

		properties.add(Property.BOSS);
		immunities.add( Dread.class );
		immunities.add( Terror.class );
		immunities.add( Sleep.class );
		immunities.add( MagicalSleep.class );
	}

	int summonCooldown = Dungeon.isChallenged(Challenges.STRONGER_BOSSES) ? 20 : 30;
	int damageTaken = 0;
	int hitCount = 0;
	int cleanCooldown = (Dungeon.isChallenged(Challenges.STRONGER_BOSSES)) ? 200 : 300;

	public boolean isDied = false;
	private static boolean telling_1 = false;
	private static boolean telling_2 = false;
	private static boolean telling_3 = false;
	private static boolean telling_4 = false;
	private static boolean telling_5 = false;

	private static final String ISDIED = "isdied";
	private static final String TELLING_1 = "telling_1";
	private static final String TELLING_2 = "telling_2";
	private static final String TELLING_3 = "telling_3";
	private static final String TELLING_4 = "telling_4";
	private static final String TELLING_5 = "telling_5";
	private static final String SUMMON_COOLDOWN = "summoncooldown";
	private static final String DMGTAKEN = "damagetaken";
	private static final String CLEAN_COOLDOWN = "cleancooldown";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put(ISDIED, isDied);
		bundle.put(TELLING_1, telling_1);
		bundle.put(TELLING_2, telling_2);
		bundle.put(TELLING_3, telling_3);
		bundle.put(TELLING_4, telling_4);
		bundle.put(TELLING_5, telling_5);
		bundle.put(SUMMON_COOLDOWN, summonCooldown);
		bundle.put(DMGTAKEN, damageTaken);
		bundle.put(CLEAN_COOLDOWN, cleanCooldown);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		isDied = bundle.getBoolean( ISDIED );
		telling_1 = bundle.getBoolean( TELLING_1 );
		telling_2 = bundle.getBoolean( TELLING_2 );
		telling_3 = bundle.getBoolean( TELLING_3 );
		telling_4 = bundle.getBoolean( TELLING_4 );
		telling_5 = bundle.getBoolean( TELLING_5 );
		summonCooldown = bundle.getInt( SUMMON_COOLDOWN );
		damageTaken = bundle.getInt( DMGTAKEN );
		cleanCooldown = bundle.getInt( CLEAN_COOLDOWN );
	}

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
		return (Dungeon.isChallenged(Challenges.STRONGER_BOSSES) ? 52 : 35);
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
		cleanCooldown--;

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
			Soldier soldier = new Soldier();
			soldier.state = soldier.HUNTING;
			soldier.pos = 3+16*33;
			GameScene.add( soldier );
			soldier.beckon(Dungeon.hero.pos);

			Researcher researcher = new Researcher();
			researcher.state = researcher.HUNTING;
			researcher.pos = 29+16*33;
			GameScene.add( researcher );
			researcher.beckon(Dungeon.hero.pos);

			Medic medic = new Medic();
			medic.state = medic.HUNTING;
			medic.pos = 16+3*33;
			GameScene.add( medic );
			medic.beckon(Dungeon.hero.pos);
			summonCooldown = (Dungeon.isChallenged(Challenges.STRONGER_BOSSES) ? 20 : 30);

			sprite.centerEmitter().start(Speck.factory(Speck.SCREAM), 0.4f, 2);
			Sample.INSTANCE.play(Assets.Sounds.CHALLENGE);
		}

		if (cleanCooldown <= 0) {
			Sample.INSTANCE.play(Assets.Sounds.BLAST, 1, 1);
			GameScene.flash(0x80FFFFFF);
			yell(Messages.get(this, "cleaning"));
			for (int i = 0; i < 1122; i++) {
				if (Dungeon.level.map[i] == Terrain.BARRICADE
						|| Dungeon.level.map[i] == Terrain.HIGH_GRASS
						|| Dungeon.level.map[i] == Terrain.GRASS
						|| Dungeon.level.map[i] == Terrain.FURROWED_GRASS
						|| Dungeon.level.map[i] == Terrain.EMBERS
						|| Dungeon.level.map[i] == Terrain.WATER  ) {
					Level.set(i, Terrain.EMPTY);
					GameScene.updateMap(i);
				}
			}

			cleanCooldown = (Dungeon.isChallenged(Challenges.STRONGER_BOSSES)) ? 200 : 300;
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

		Statistics.rebelKilled = true;
		Badges.validateMedicUnlock();
	}

	@Override
	public void damage( int dmg, Object src ) {
		if (dmg > 100) {
			dmg = 100;
		}
		damageTaken += dmg;
		if (damageTaken >= 250) {
			Buff.affect(this, Barrier.class).setShield(100);
			damageTaken = 0;
		}
		hitCount ++;
		if (hitCount > Random.Int( Dungeon.isChallenged(Challenges.STRONGER_BOSSES) ? 5 : 10 ) && this.buff(Paralysis.class) == null) {
			int newPos;
			LabsBossLevel level = (LabsBossLevel) Dungeon.level;
			if (Dungeon.level != null) {
				do {
					newPos = level.randomCellPos();
				} while (level.map[newPos] == Terrain.BARRICADE || Actor.findChar(newPos) != null);

				if (level.heroFOV[pos]) CellEmitter.get( pos ).burst( Speck.factory( Speck.WOOL ), 6 );

				sprite.move( pos, newPos );
				move( newPos );
				sprite.idle();

				if (level.heroFOV[newPos]) CellEmitter.get( newPos ).burst( Speck.factory( Speck.WOOL ), 6 );
				Sample.INSTANCE.play( Assets.Sounds.PUFF );
				if (Dungeon.isChallenged(Challenges.STRONGER_BOSSES)) {
					GameScene.flash( 0x80FFFFFF );
					Sample.INSTANCE.play( Assets.Sounds.BLAST );
					Buff.affect(Dungeon.hero, Blindness.class, 5f);
				}
			}
			hitCount = 0;
		}

		super.damage(dmg, src);
	}
	
}
